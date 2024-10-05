package trainingBot.service.redis;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class SearchDataService {

    private static final String SEARCH_DATA_PREFIX = "_search_data:";
    private final RedisTemplate<String, Object> redisTemplate;
    private final HashOperations<String, String, Object> hashOps;

    public SearchDataService(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
        this.hashOps = redisTemplate.opsForHash();
    }

    public void setCity(long userId, String city) {
        hashOps.put(userId + SEARCH_DATA_PREFIX, "city", city);
    }

    public String getCity(long userId) {
        Object cityObject = hashOps.get(userId + SEARCH_DATA_PREFIX, "city");
        if (cityObject instanceof String) {
            return cityObject.toString();
        }
        return null;
    }
}
