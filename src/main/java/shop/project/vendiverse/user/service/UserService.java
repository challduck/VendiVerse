package shop.project.vendiverse.user.service;

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
import shop.project.vendiverse.exception.ExceptionCode;
import shop.project.vendiverse.exception.ExceptionResponse;
import shop.project.vendiverse.security.TokenService;
import shop.project.vendiverse.security.UserDetailsImpl;
import shop.project.vendiverse.user.controller.dto.request.EmailVerifiedRequest;
import shop.project.vendiverse.user.controller.dto.request.SignInRequest;
import shop.project.vendiverse.user.controller.dto.request.SignUpRequest;
import shop.project.vendiverse.user.controller.dto.request.UserPasswordUpdateRequest;
import shop.project.vendiverse.user.controller.dto.response.UserSignInResponse;
import shop.project.vendiverse.user.entity.User;
import shop.project.vendiverse.user.repository.UserRepository;
import shop.project.vendiverse.util.RedisUtil;
import shop.project.vendiverse.util.Role;

import java.util.Collections;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;


@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {

    @Value("${SPRING_SMTP_USERNAME}")
    private String env;
    private final UserRepository userRepository;
    private final JavaMailSender mailSender;
    private final RedisUtil redisUtil;
    private final TokenService tokenService;

    @Transactional
    public void save(SignUpRequest request) {

        if (userRepository.existsByEmail(request.email())){
            throw new ExceptionResponse(ExceptionCode.DUPLICATE_EMAIL);
        }

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

//      TODO: 실제 메일 발송시, mailSend() 주석 해제하고 아래 redis 저장 내역 주석처리
//        mailSend(request.getEmail(), subject, text);
        String code = randomVerifiedCode();
        redisUtil.setDataExpire(request.email(), code, 60 * 5L, TimeUnit.SECONDS);

        userRepository.save(User.builder()
                .detailAddress(request.detailAddress())
                .address(request.address())
                .email(request.email())
                .name(request.name())
                .password(encoder.encode(request.password()))
                .phoneNumber(request.phoneNumber())
                        .roles(Collections.singletonList(Role.USER))
                .build());
    }

    public boolean checkVerificationCode(EmailVerifiedRequest request) {
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
            helper.setFrom(env);
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

    public UserSignInResponse signIn(SignInRequest dto) {
        String deviceId = UUID.randomUUID().toString();

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

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

    public void logout(String accessToken, String userEmail, String deviceId){
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(()-> new ExceptionResponse(ExceptionCode.NOT_FOUND_USER));

        tokenService.deleteRefreshToken(user, deviceId);
        tokenService.blockedOneAccessToken(accessToken, deviceId, user);
    }

    public void allLogout(String userEmail){
        User user = userRepository.findByEmail(userEmail)
            .orElseThrow(()-> new ExceptionResponse(ExceptionCode.NOT_FOUND_USER));

        tokenService.deleteAllRefreshToken(user);
        tokenService.blockAllAccessTokens(user);
    }

    public User userPasswordUpdate(UserDetailsImpl userDetails, UserPasswordUpdateRequest dto){
        String email = userDetails.getUsername();
        User user = userRepository.findByEmail(email)
                .orElseThrow(()-> new ExceptionResponse(ExceptionCode.NOT_FOUND_USER));
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        if (!passwordEncoder.matches(dto.oldPassword(), user.getPassword())){
            throw new ExceptionResponse(ExceptionCode.NOT_MATCH_PASSWORD);
        }
        userRepository.save(user.passwordUpdate(dto.newPassword()));
        return user;
    }

    public User findUserById(Long id){
        return userRepository.findById(id)
                .orElseThrow(()-> new ExceptionResponse(ExceptionCode.NOT_FOUND_USER));
    }

    public User findUserByEmail(String email){
        return userRepository.findByEmail(email)
                .orElseThrow(()-> new ExceptionResponse(ExceptionCode.NOT_FOUND_USER));
    }
}
