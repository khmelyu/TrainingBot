package trainingBot.controller.service.redis;

import org.springframework.stereotype.Service;
import org.springframework.data.redis.core.RedisTemplate;


@Service
public class UserStateService {

    private final RedisTemplate<String, Object> redisTemplate;
    private static final String USER_STATE_PREFIX = "user_state:";

    public UserStateService(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }


    public void saveUserState(String userId, UserState state) {
        redisTemplate.opsForValue().set(USER_STATE_PREFIX + userId, state);
    }

    public UserState getUserState(String userId) {
        return (UserState) redisTemplate.opsForValue().get(USER_STATE_PREFIX + userId);
    }

    public void clearUserState(String userId) {
        redisTemplate.delete(USER_STATE_PREFIX + userId);
    }
}

