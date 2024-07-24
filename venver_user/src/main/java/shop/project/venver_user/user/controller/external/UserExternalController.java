package shop.project.venver_user.user.controller.external;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import shop.project.venver_user.user.controller.external.dto.request.*;
import shop.project.venver_user.user.service.dto.req.*;
import shop.project.venver_user.user.service.dto.res.UserInfoResponse;
import shop.project.venver_user.user.service.dto.res.UserSignInResponse;
import shop.project.venver_user.user.service.UserService;


@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
@Slf4j
public class UserExternalController {

    private final UserService userService;
    private final static String HEADER_DEVICE_ID = "Device-Id";

    @PostMapping("/sign-up")
    public ResponseEntity<String> signUp(@RequestBody SignUpControllerRequest controllerRequest) {

        SignUpServiceRequest request = new SignUpServiceRequest(controllerRequest);

        userService.signUp(request);
        return ResponseEntity.ok("회원가입 성공, 이메일 인증 요청");
    }

    @PostMapping("/email-check")
    public ResponseEntity<String> userVerifiedEmail(@RequestBody EmailVerifiedControllerRequest controllerRequest) {
        EmailVerifiedServiceRequest request = new EmailVerifiedServiceRequest(controllerRequest);
        if (userService.checkVerificationCode(request)){
            return ResponseEntity.ok("이메일이 확인되었습니다.");
        } else {
            return ResponseEntity.badRequest().body("전송된 코드와 일치하지 않습니다.");
        }
    }

    @PostMapping("/sign-in")
    public ResponseEntity<String> signIn(@RequestBody SignInControllerRequest dto, HttpServletResponse response){

        SignInServiceRequest request = new SignInServiceRequest(dto);

        UserSignInResponse userSignInResponse = userService.signIn(request);

        response.addCookie(userSignInResponse.refreshTokenCookie());

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + userSignInResponse.accessToken());
        headers.add(HEADER_DEVICE_ID, userSignInResponse.deviceId());

        return ResponseEntity.ok().headers(headers).body("로그인 성공 " + userSignInResponse.accessToken());
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestHeader(name = "userId") Long userId, HttpServletRequest request){

        String accessToken = getAccessTokenFromRequest(request);
        String deviceId = request.getHeader(HEADER_DEVICE_ID);

        if (userId == null){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("유효한 접근이 아닙니다.");
        }

        userService.logout(accessToken, userId, deviceId);

        return ResponseEntity.ok().body("로그아웃 성공");
    }

    @PostMapping("/logout-all")
    public ResponseEntity<?> allLogout(@RequestHeader(name = "userId") Long userId){
        userService.allLogout(userId);
        return ResponseEntity.ok().body("로그아웃 성공");
    }

    @GetMapping("/user-info")
    public ResponseEntity<UserInfoResponse> getUserInfo(@RequestHeader(name = "userId") Long userId){

        UserInfoResponse response = userService.getUserInfo(userId);

        return ResponseEntity.ok().body(response);
    }

    @PatchMapping("/update-info")
    public ResponseEntity<Void> updateUserInfo(@RequestHeader(name = "userId") Long userId, @RequestBody UserInfoUpdateRequest request){
        UserInfoUpdateServiceRequest serviceRequest = new UserInfoUpdateServiceRequest(request);
        userService.userInfoUpdate(userId, serviceRequest);

        return ResponseEntity.ok().build();
    }

    @PatchMapping("/update-pw")
    public ResponseEntity<Void> updateUserPassword(@RequestHeader(name = "userId") Long userId, @RequestBody UserPasswordUpdateControllerRequest request){
        UserPasswordUpdateServiceRequest serviceRequest = new UserPasswordUpdateServiceRequest(request);
        userService.userPasswordUpdate(userId, serviceRequest);
        return ResponseEntity.ok().build();
    }

    private String getAccessTokenFromRequest(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            return authHeader.substring("Bearer ".length());
        }
        return null;
    }

}
