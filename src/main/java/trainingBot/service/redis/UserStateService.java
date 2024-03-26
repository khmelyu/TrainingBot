package trainingBot.service.redis;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;


@Service
public class UserStateService {

    private static final String USER_STATE_PREFIX = "user_state:";
    private final RedisTemplate<String, Object> redisTemplate;

    public UserStateService(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }


    public void setUserState(long userId, UserState state) {
        redisTemplate.opsForValue().set(userId + USER_STATE_PREFIX, state);
    }

    public UserState getUserState(long userId) {
        Object stateObject = redisTemplate.opsForValue().get(userId + USER_STATE_PREFIX);
        if (stateObject instanceof String) {
            String stateString = stateObject.toString();
            return UserState.valueOf(stateString);
        }
        return null;
    }
}