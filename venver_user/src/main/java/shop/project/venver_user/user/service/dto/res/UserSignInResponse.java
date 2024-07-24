package shop.project.venver_user.user.service.dto.res;

import jakarta.servlet.http.Cookie;
import lombok.Builder;
import shop.project.venver_user.user.entity.User;

@Builder
public record UserSignInResponse(User user, String accessToken, String deviceId, Cookie refreshTokenCookie) {
}
