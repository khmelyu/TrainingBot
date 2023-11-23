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


    public void setUserState(Long userId, UserState state) {
        redisTemplate.opsForValue().set(userId + USER_STATE_PREFIX, state);
    }

    public UserState getUserState(Long userId) {
        Object stateObject = redisTemplate.opsForValue().get(userId + USER_STATE_PREFIX);
        if (stateObject instanceof String) {
            String stateString = stateObject.toString();
            return UserState.valueOf(stateString);
        }
        return null;
    }


    public void clearUserState(Long userId) {
        redisTemplate.delete(userId + USER_STATE_PREFIX);
    }
}