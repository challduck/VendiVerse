package shop.project.venver_user.util;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class RedisUtil {
    private final StringRedisTemplate stringRedisTemplate;

    public String getData(String key){
        ValueOperations<String, String> ops = stringRedisTemplate.opsForValue();
        return ops.get(key);
    }

    public void setData(String key, String value){
        ValueOperations<String, String> ops = stringRedisTemplate.opsForValue();
        ops.set(key, value);
    }

    public void setDataExpire(String key,String value, long expire, TimeUnit timeUnit){
        ValueOperations<String, String> ops = stringRedisTemplate.opsForValue();
        ops.set(key, value, expire, timeUnit);
    }

    public void deleteData(String key){
        stringRedisTemplate.delete(key);
    }
}
