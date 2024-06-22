package shop.project.vendiverse.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.stereotype.Service;
import shop.project.vendiverse.config.jwt.TokenProvider;
import shop.project.vendiverse.domain.User;

import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class TokenService {

    private final TokenProvider tokenProvider;
    private final RedisTemplate<String, Object> redisObjectTemplate;

    private final static String REFRESH_TOKEN_PREFIX = "refresh_token:";
    private final static String ACCESS_TOKEN_PREFIX = "access_token:";
    private final static String BLOCKED_ACCESS_TOKEN_PREFIX = "blocked_access_token:";

    public static final Duration REFRESH_TOKEN_DURATION = Duration.ofMillis(14 * 24 * 60 * 60 * 1000);
    public static final Duration ACCESS_TOKEN_DURATION = Duration.ofMillis(8 * 60 * 60 * 1000);

    public String loginSuccessUserGenerateRefreshToken(User user, String deviceId) {
        String refreshToken = tokenProvider.generateToken(user, REFRESH_TOKEN_DURATION);
        saveRefreshToken(user, refreshToken, deviceId);
        return refreshToken;
    }

    public String loginSuccessUserGenerateAccessToken(User user, String deviceId) {
        String accessToken = tokenProvider.generateToken(user, ACCESS_TOKEN_DURATION);
        saveAccessToken(user, accessToken, deviceId);
        return accessToken;
    }

    public void saveRefreshToken(User user, String newRefreshToken, String deviceId) {
        String key = REFRESH_TOKEN_PREFIX + user.getEmail() + ":" + deviceId;
        redisObjectTemplate.opsForValue().set(key, newRefreshToken);
        redisObjectTemplate.expire(key, REFRESH_TOKEN_DURATION);
    }

    public void saveAccessToken(User user, String accessToken, String deviceId) {
        String key = ACCESS_TOKEN_PREFIX + user.getEmail() + ":" + deviceId;
        redisObjectTemplate.opsForValue().set(key, accessToken);
        redisObjectTemplate.expire(key, ACCESS_TOKEN_DURATION);
    }

    public void deleteRefreshToken(User user, String deviceId) {
        String key = REFRESH_TOKEN_PREFIX + user.getEmail() + ":" + deviceId;
        redisObjectTemplate.delete(key);
    }

    public void deleteAllRefreshToken(User user) {
        String pattern = REFRESH_TOKEN_PREFIX+ user.getEmail() + ":*";
        Cursor<byte[]> cursor = redisObjectTemplate.getConnectionFactory().getConnection().scan(ScanOptions.scanOptions().match(pattern).count(1000).build());
        while (cursor.hasNext()) {
            redisObjectTemplate.delete(new String(cursor.next()));
        }
    }

    public void blockAllAccessTokens(User user) {
        List<String> allAccessTokens = getAllAccessTokens(user.getEmail());
        for (String accessTokenKey : allAccessTokens) {
            String accessToken = (String) redisObjectTemplate.opsForValue().get(accessTokenKey);
            if (accessToken != null) {
                blockedAccessToken(accessToken);
                redisObjectTemplate.delete(accessTokenKey);
            }
        }
    }

    public void blockedAccessToken(String accessToken) {
        String key = BLOCKED_ACCESS_TOKEN_PREFIX + accessToken;
        redisObjectTemplate.opsForValue().set(key, true);
        redisObjectTemplate.expire(key, ACCESS_TOKEN_DURATION);
    }

    public void blockedOneAccessToken(String accessToken, String deviceId, User user) {
        String key = BLOCKED_ACCESS_TOKEN_PREFIX + accessToken;
        String accessTokenKey = ACCESS_TOKEN_PREFIX + user.getEmail() + ":" + deviceId;

        redisObjectTemplate.delete(accessTokenKey);
        redisObjectTemplate.opsForValue().set(key, true);
        redisObjectTemplate.expire(key, ACCESS_TOKEN_DURATION);
    }

    public boolean isAccessTokenBlocked(String accessToken) {
        String key = BLOCKED_ACCESS_TOKEN_PREFIX + accessToken;

        Object value = redisObjectTemplate.opsForValue().get(key);
        if (value == null) {
            return false;
        }
        return "true".equals(value.toString());
    }

    private List<String> getAllAccessTokens(String email) {
        return redisObjectTemplate.keys(ACCESS_TOKEN_PREFIX + email + ":*")
                .stream()
                .map(key -> (String) key)
                .collect(Collectors.toList());
    }
}
