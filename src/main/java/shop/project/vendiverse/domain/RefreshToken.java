package shop.project.vendiverse.domain;


import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

@AllArgsConstructor
@Getter
@RedisHash(value = "jwtToken", timeToLive = 24*60*60)
public class RefreshToken {

    @Id
    private String id;

    private String refreshToken;
    @Indexed
    private String accessToken;
}
