package trainingBot.service.redis;

import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;


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

    public void setTrainingName(long userId, String trainingName) {
        hashOps.put(userId + USER_DATA_PREFIX, "name", trainingName);
    }

    public String getTrainingName(long userId) {
        Object Object = hashOps.get(userId + USER_DATA_PREFIX, "name");
        if (Object instanceof String) {
            return Object.toString();
        }
        return null;
    }

    public void setTrainingDescription(long userId, String description) {
        hashOps.put(userId + USER_DATA_PREFIX, "description", description);
    }

    public String getTrainingDescription(long userId) {
        Object Object = hashOps.get(userId + USER_DATA_PREFIX, "description");
        if (Object instanceof String) {
            return Object.toString();
        }
        return null;
    }

    public void setMaxUsers(long userId, String maxUsers) {
        hashOps.put(userId + USER_DATA_PREFIX, "max_users", maxUsers);
    }

    public String getMaxUsers(long userId) {
        Object Object = hashOps.get(userId + USER_DATA_PREFIX, "max_users");
        if (Object instanceof String) {
            return Object.toString();
        }
        return null;
    }

    public void setPic(long userId, String pic) {
        hashOps.put(userId + USER_DATA_PREFIX, "pic", pic);
    }

    public String getPic(long userId) {
        Object Object = hashOps.get(userId + USER_DATA_PREFIX, "pic");
        if (Object instanceof String) {
            return Object.toString();
        }
        return null;
    }

    public void setTrainingDate(long userId, String date) {
        hashOps.put(userId + USER_DATA_PREFIX, "date", date);
    }

    public String getTrainingDate(long userId) {
        Object Object = hashOps.get(userId + USER_DATA_PREFIX, "date");
        if (Object instanceof String) {
            return Object.toString();
        }
        return null;
    }

    public void setStartTime(long userId, String startTime) {
        hashOps.put(userId + USER_DATA_PREFIX, "start_time", startTime);
    }

    public String getStartTime(long userId) {
        Object Object = hashOps.get(userId + USER_DATA_PREFIX, "start_time");
        if (Object instanceof String) {
            return Object.toString();
        }
        return null;
    }

    public void setEndTime(long userId, String endTime) {
        hashOps.put(userId + USER_DATA_PREFIX, "end_time", endTime);
    }

    public String getEndTime(long userId) {
        Object Object = hashOps.get(userId + USER_DATA_PREFIX, "end_time");
        if (Object instanceof String) {
            return Object.toString();
        }
        return null;
    }

    public void setLink(long userId, String link) {
        hashOps.put(userId + USER_DATA_PREFIX, "link", link);
    }

    public String getLink(long userId) {
        Object Object = hashOps.get(userId + USER_DATA_PREFIX, "link");
        if (Object instanceof String) {
            return Object.toString();
        }
        return null;
    }

    public void clearTemplate(long id) {
        redisTemplate.delete(List.of(id + "_user_data:"));

    }
}