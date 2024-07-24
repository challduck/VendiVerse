package shop.project.venver_user.user.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.servlet.http.Cookie;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.project.venver_user.exception.ExceptionCode;
import shop.project.venver_user.exception.ExceptionResponse;
import shop.project.venver_user.user.entity.Role;
import shop.project.venver_user.user.entity.User;
import shop.project.venver_user.user.repository.UserRepository;
import shop.project.venver_user.user.service.dto.req.*;
import shop.project.venver_user.user.service.dto.res.UserInfoResponse;
import shop.project.venver_user.user.service.dto.res.UserSignInResponse;
import shop.project.venver_user.util.RedisUtil;

import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;


@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {

    @Value("${spring.mail.username}")
    private String mailUsername;
    private final UserRepository userRepository;
    private final JavaMailSender mailSender;
    private final RedisUtil redisUtil;
    private final TokenService tokenService;
    private final BCryptPasswordEncoder encoder;

    @Transactional
    public void signUp(SignUpServiceRequest request) {

        if (userRepository.existsByEmail(request.email())){
            throw new ExceptionResponse(ExceptionCode.DUPLICATE_EMAIL);
        }

//      TODO: 실제 메일 발송시, mailSend() 주석 해제하고 아래 redis 저장 내역 주석처리
//        mailSend(request.getEmail(), subject, text);
        String code = randomVerifiedCode();
        redisUtil.setDataExpire(request.email(), code, 60 * 5L, TimeUnit.SECONDS);

        userRepository.save(User.builder()
                .detailAddress(request.detailAddress())
                .address(request.address())
                .email(request.email())
                .name(request.name())
                .emailVerified(false)
                .password(encoder.encode(request.password()))
                .phoneNumber(request.phoneNumber())
                .role(Role.USER)
                .build());
    }

    public boolean checkVerificationCode(EmailVerifiedServiceRequest request) {
        String value = redisUtil.getData(request.email());
        if (Objects.equals(value, request.verifiedCode())){
            redisUtil.deleteData(request.email());
            return true;
        } else return false;
    }

    @Transactional
    public void mailSend(String to, String subject, String content){
        MimeMessage msg = mailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(msg, true, "utf-8");
            helper.setFrom(mailUsername);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(content, true);
            mailSender.send(msg);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }

        String code = randomVerifiedCode();
        redisUtil.setDataExpire(to, code, 60 * 5L, TimeUnit.SECONDS);
    }

    private String randomVerifiedCode(){
        return Integer.toString(ThreadLocalRandom.current().nextInt(100000, 999999 + 1));
    }

    public UserSignInResponse signIn(SignInServiceRequest dto) {
        String deviceId = UUID.randomUUID().toString();

        User user = userRepository.findByEmail(dto.email())
                .orElseThrow(()-> new ExceptionResponse(ExceptionCode.NOT_FOUND_USER));

        if (!encoder.matches(dto.password(), user.getPassword()) || user.getEmail() == null){
            throw new ExceptionResponse(ExceptionCode.INCORRECT_ID_OR_PASSWORD);
        }

        String refreshToken = tokenService.loginSuccessUserGenerateRefreshToken(user, deviceId);
        String accessToken = tokenService.loginSuccessUserGenerateAccessToken(user, deviceId);

        Cookie refreshTokenCookie = new Cookie("refresh_token", refreshToken);
        refreshTokenCookie.setHttpOnly(true);
        refreshTokenCookie.setMaxAge(7 * 24 * 60 * 60);
        refreshTokenCookie.setPath("/");

        return UserSignInResponse.builder()
                .user(user)
                .refreshTokenCookie(refreshTokenCookie)
                .accessToken(accessToken)
                .deviceId(deviceId)
                .build();
    }

    public void logout(String accessToken, Long userId, String deviceId){
        User user = userRepository.findById(userId)
                .orElseThrow(()-> new ExceptionResponse(ExceptionCode.NOT_FOUND_USER));

        tokenService.deleteRefreshToken(user, deviceId);
        tokenService.blockedOneAccessToken(accessToken, deviceId, user);
    }

    public void allLogout(Long userId){
        User user = userRepository.findById(userId)
            .orElseThrow(()-> new ExceptionResponse(ExceptionCode.NOT_FOUND_USER));

        tokenService.deleteAllRefreshToken(user);
        tokenService.blockAllAccessTokens(user);
    }

    public void userInfoUpdate(Long userId, UserInfoUpdateServiceRequest request){
        User user = findUserById(userId);
        user.userInfoUpdate(request.phoneNumber(), request.address(), request.detailAddress());
        userRepository.save(user);
    }

    public void userPasswordUpdate(Long userId, UserPasswordUpdateServiceRequest dto){
        User user = userRepository.findById(userId)
                .orElseThrow(()-> new ExceptionResponse(ExceptionCode.NOT_FOUND_USER));

        if (!encoder.matches(dto.oldPassword(), user.getPassword())){
            throw new ExceptionResponse(ExceptionCode.NOT_MATCH_PASSWORD);
        }

        user.passwordUpdate(dto.newPassword());
        userRepository.save(user);
    }

    public User findUserById(Long id){
        return userRepository.findById(id)
                .orElseThrow(()-> new ExceptionResponse(ExceptionCode.NOT_FOUND_USER));
    }

    public UserInfoResponse getUserInfo(Long userId){
        User user = findUserById(userId);

        return UserInfoResponse.builder()
                .address(user.getAddress())
                .name(user.getName())
                .detailAddress(user.getDetailAddress())
                .phoneNumber(user.getPhoneNumber())
                .isEmailVerified(user.isEmailVerified())
                .build();
    }
}
