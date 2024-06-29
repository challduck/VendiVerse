package shop.project.vendiverse.user.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import shop.project.vendiverse.user.controller.dto.request.EmailVerifiedRequest;
import shop.project.vendiverse.user.controller.dto.request.SignInRequest;
import shop.project.vendiverse.user.controller.dto.request.SignUpRequest;
import shop.project.vendiverse.user.controller.dto.response.UserSignInResponse;
import shop.project.vendiverse.user.service.UserService;


@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;
    private final static String HEADER_DEVICE_ID = "Device-Id";

    @PostMapping("/sign-up")
    public ResponseEntity<String> signUp(@RequestBody SignUpRequest request) {
        userService.save(request);
        return ResponseEntity.ok("회원가입 성공, 이메일 인증 요청");
    }

    @PostMapping("/email-check")
    public ResponseEntity<String> userVerifiedEmail(@RequestBody EmailVerifiedRequest request) {
        if (userService.checkVerificationCode(request)){
            return ResponseEntity.ok("이메일이 확인되었습니다.");
        } else {
            return ResponseEntity.badRequest().body("전송된 코드와 일치하지 않습니다.");
        }
    }

    @PostMapping("/sign-in")
    public ResponseEntity<?> signIn(@RequestBody SignInRequest dto, HttpServletRequest request, HttpServletResponse response){
            UserSignInResponse userSignInResponse = userService.signIn(dto);

            response.addCookie(userSignInResponse.getRefreshTokenCookie());

            HttpHeaders headers = new HttpHeaders();
            headers.add("Authorization", "Bearer " + userSignInResponse.getAccessToken());
            headers.add(HEADER_DEVICE_ID, userSignInResponse.getDeviceId());

            return ResponseEntity.ok().headers(headers).body("로그인 성공 " + userSignInResponse.getAccessToken());
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletRequest request){

        String accessToken = getAccessTokenFromRequest(request);
        String userEmail = getUserEmailFromRequest(request);
        String deviceId = request.getHeader(HEADER_DEVICE_ID);

        if (userEmail == null){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("유효한 접근이 아닙니다.");
        }

        userService.logout(accessToken, userEmail, deviceId);

        return ResponseEntity.ok().body("로그아웃 성공");
    }

    @PostMapping("/logout-all")
    public ResponseEntity<?> allLogout(HttpServletRequest request){
        userService.allLogout(getUserEmailFromRequest(request));
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
