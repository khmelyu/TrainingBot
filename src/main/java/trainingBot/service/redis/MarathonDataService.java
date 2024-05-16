package trainingBot.service.redis;

import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MarathonDataService {
    private static final String MARATHON_DATA_PREFIX = "_marathon_data:";
    private final RedisTemplate<String, Object> redisTemplate;
    private final HashOperations<String, String, Object> hashOps;

    public MarathonDataService(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
        this.hashOps = redisTemplate.opsForHash();
    }

    public void setSex(long userId, String sex) {
        hashOps.put(userId + MARATHON_DATA_PREFIX, "sex", sex);
    }

    public String getSex(long userId) {
        Object categoryObject = hashOps.get(userId + MARATHON_DATA_PREFIX, "sex");
        if (categoryObject instanceof String) {
            return categoryObject.toString();
        }
        return null;
    }

    public void setTime(long userId, String time) {
        hashOps.put(userId + MARATHON_DATA_PREFIX, "time", time);
    }

    public String getTime(long userId) {
        Object categoryObject = hashOps.get(userId + MARATHON_DATA_PREFIX, "time");
        if (categoryObject instanceof String) {
            return categoryObject.toString();
        }
        return null;
    }

    public void clearTemplate(long id) {
        redisTemplate.delete(List.of(id + "_user_data:"));

    }

}
