package trainingBot.service.redis;

import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;


@Service
public class UserStateService {

    private static final String USER_STATE_PREFIX = "_user_state:";
    private static final String USER_DATA_PREFIX = "_user_data:";
    private final RedisTemplate<String, Object> redisTemplate;
    private final HashOperations<String, String, Object> hashOps;

    public UserStateService(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
        this.hashOps = redisTemplate.opsForHash();
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


    public void setCity(long userId, String city) {
        hashOps.put(userId + USER_DATA_PREFIX, "city", city);
    }

    public String getCity(long userId) {
        Object Object = hashOps.get(userId + USER_DATA_PREFIX, "city");
        if (Object instanceof String) {
            return Object.toString();
        }
        return null;
    }

    public void setCategory(long userId, String category) {
        hashOps.put(userId + USER_DATA_PREFIX, "category", category);
    }

    public String getCategory(long userId) {
        Object Object = hashOps.get(userId + USER_DATA_PREFIX, "category");
        if (Object instanceof String) {
            return Object.toString();
        }
        return null;
    }
}