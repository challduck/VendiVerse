package shop.project.vendiverse.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.project.vendiverse.config.RedisConfig;
import shop.project.vendiverse.domain.User;
import shop.project.vendiverse.dto.user.SignInUserRequest;
import shop.project.vendiverse.dto.user.SignUpUserRequest;
import shop.project.vendiverse.exception.ExceptionCode;
import shop.project.vendiverse.exception.ExceptionResponse;
import shop.project.vendiverse.repository.UserRepository;
import shop.project.vendiverse.util.RedisUtil;

import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {

    @Value("${SPRING_SMTP_USERNAME}")
    private String env;
    @Autowired
    private final UserRepository userRepository;
    @Autowired
    private JavaMailSender mailSender;
    @Autowired
    private RedisConfig redisConfig;
    @Autowired
    private RedisUtil redisUtil;

    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(()-> new ExceptionResponse(ExceptionCode.NOT_FOUND_USER));
    }

    public User findByUserId(Long id){
        return userRepository.findById(id)
                .orElseThrow(()-> new ExceptionResponse(ExceptionCode.NOT_FOUND_USER));
    }

    @Transactional
    public User save(SignUpUserRequest request) {

        if (userRepository.existsByEmail(request.getEmail())){
            throw new ExceptionResponse(ExceptionCode.DUPLICATE_EMAIL);
        }

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

//      TODO: 실제 메일 발송시, mailSend() 주석 해제하고 아래 redis 저장 내역 주석처리
//        mailSend(request.getEmail(), subject, text);
        String code = randomVerifiedCode();
        redisUtil.setDataExpire(request.getEmail(), code, 60 * 5L, TimeUnit.SECONDS);

        return userRepository.save( User.builder()
                        .address(request.getAddress())
                        .email(request.getEmail())
                        .name(request.getName())
                        .password(encoder.encode(request.getPassword()))
                        .phone_number(request.getPhoneNumber())
                .build());
    }

    public boolean checkVerificationCode(String email, String authCode) {
        String value = redisUtil.getData(email);
        if (Objects.equals(value, authCode)){
            redisUtil.deleteData(email);
            return true;
        } else return false;
    }

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

    public User signIn(SignInUserRequest dto) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        User user = userRepository.findByEmail(dto.getEmail())
                .orElseThrow(()-> new ExceptionResponse(ExceptionCode.NOT_FOUND_USER));

        if (!encoder.matches(dto.getPassword(), user.getPassword()) || user.getEmail() == null){
            throw new ExceptionResponse(ExceptionCode.INCORRECT_ID_OR_PASSWORD);
        }
        return user;
    }
}
