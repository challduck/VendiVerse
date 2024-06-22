package shop.project.vendiverse.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import shop.project.vendiverse.domain.User;
import shop.project.vendiverse.dto.user.SignInUserRequest;
import shop.project.vendiverse.dto.user.SignUpUserRequest;
import shop.project.vendiverse.exception.ExceptionCode;
import shop.project.vendiverse.exception.ExceptionResponse;
import shop.project.vendiverse.service.TokenService;
import shop.project.vendiverse.service.UserService;

import java.util.UUID;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;
    private final TokenService tokenService;
    private final static String HEADER_DEVICE_ID = "Device-Id";

    @PostMapping("/sign-up")
    public ResponseEntity<String> signUp(@RequestBody SignUpUserRequest request) {
        try{
            userService.save(request);
            return ResponseEntity.ok("회원가입을 축하드립니다. \n 이메일 인증을 진행해 주세요.");
        } catch (Exception e) {
            throw new ExceptionResponse(ExceptionCode.DUPLICATE_EMAIL);
        }
    }

    @PostMapping("/email-check")
    public ResponseEntity<String> userVerifiedEmail(@RequestBody String email, String code) {
        if (userService.checkVerificationCode(email, code)){
            return ResponseEntity.ok("이메일이 확인되었습니다.");
        } else {
            return ResponseEntity.badRequest().body("전송된 코드와 일치하지 않습니다.");
        }
    }

    @PostMapping("/sign-in")
    public ResponseEntity<?> signIn(@RequestBody SignInUserRequest dto, HttpServletRequest request, HttpServletResponse response){
        try{
            User user = userService.signIn(dto);
            String deviceId = UUID.randomUUID().toString();

            String refreshToken = tokenService.loginSuccessUserGenerateRefreshToken(user, deviceId);
            String accessToken = tokenService.loginSuccessUserGenerateAccessToken(user, deviceId);

            Cookie refreshTokenCookie = new Cookie("refresh_token", refreshToken);
            refreshTokenCookie.setMaxAge(7 * 24 * 60 * 60);
            refreshTokenCookie.setPath("/");

            response.addCookie(refreshTokenCookie);

            HttpHeaders headers = new HttpHeaders();
            headers.add("Authorization", "Bearer " + accessToken);
            headers.add(HEADER_DEVICE_ID, deviceId);

            return ResponseEntity.ok().headers(headers).body("로그인 성공 " + accessToken);
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletRequest request){

        String accessToken = getAccessTokenFromRequest(request);
        String userEmail = getUserEmailFromRequest(request);
        String deviceId = request.getHeader(HEADER_DEVICE_ID);

        if (userEmail == null){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("유효한 접근이 아닙니다.");
        }

        User user = userService.findByEmail(userEmail);

        tokenService.deleteRefreshToken(user, deviceId);
        tokenService.blockedOneAccessToken(accessToken, deviceId, user);

        return ResponseEntity.ok().body("로그아웃 성공");
    }

    @PostMapping("/logout-all")
    public ResponseEntity<?> allLogout(HttpServletRequest request){
        User user = userService.findByEmail(getUserEmailFromRequest(request));

        tokenService.deleteAllRefreshToken(user);
        tokenService.blockAllAccessTokens(user);

        return ResponseEntity.ok().body("로그아웃 성공");
    }

    private String getAccessTokenFromRequest(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            return authHeader.substring("Bearer ".length());
        }
        return null;
    }

    private String getUserEmailFromRequest(HttpServletRequest request) {
        return request.getUserPrincipal().getName();
    }
}
