package shop.project.vendiverse.user.controller.dto.response;

import jakarta.servlet.http.Cookie;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import shop.project.vendiverse.user.entity.User;

@Builder
@Getter
@RequiredArgsConstructor
public class UserSignInResponse {
    private final User user;
    private final String accessToken;
    private final String deviceId;
    private final Cookie refreshTokenCookie;


}
