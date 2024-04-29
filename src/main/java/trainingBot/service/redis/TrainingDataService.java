package trainingBot.service.redis;

import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class TrainingDataService {

    private static final String USER_DATA_PREFIX = "_user_data:";
    private final RedisTemplate<String, Object> redisTemplate;
    private final HashOperations<String, String, Object> hashOps;

    public TrainingDataService(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
        this.hashOps = redisTemplate.opsForHash();
    }

    public void setTrainingId(long userId, String trainingId) {
        hashOps.put(userId + USER_DATA_PREFIX, "training_id", trainingId);
    }

    public String getTrainingId(long userId) {
        Object trainingIdObject = hashOps.get(userId + USER_DATA_PREFIX, "training_id");
        if (trainingIdObject instanceof String) {
            UUID trainingId = UUID.fromString(trainingIdObject.toString());
            return trainingId.toString();
        }
        return null;
    }

    public void setCity(long userId, String city) {
        hashOps.put(userId + USER_DATA_PREFIX, "city", city);
    }

    public String getCity(long userId) {
        Object cityObject = hashOps.get(userId + USER_DATA_PREFIX, "city");
        if (cityObject instanceof String) {
            return cityObject.toString();
        }
        return null;
    }

    public void setCategory(long userId, String category) {
        hashOps.put(userId + USER_DATA_PREFIX, "category", category);
    }

    public String getCategory(long userId) {
        Object categoryObject = hashOps.get(userId + USER_DATA_PREFIX, "category");
        if (categoryObject instanceof String) {
            return categoryObject.toString();
        }
        return null;
    }

    public void setTrainingName(long userId, String trainingName) {
        hashOps.put(userId + USER_DATA_PREFIX, "name", trainingName);
    }

    public String getTrainingName(long userId) {
        Object trainingNameObject = hashOps.get(userId + USER_DATA_PREFIX, "name");
        if (trainingNameObject instanceof String) {
            return trainingNameObject.toString();
        }
        return null;
    }

    public void setTrainingDescription(long userId, String description) {
        hashOps.put(userId + USER_DATA_PREFIX, "description", description);
    }

    public String getTrainingDescription(long userId) {
        Object descriptionObject = hashOps.get(userId + USER_DATA_PREFIX, "description");
        if (descriptionObject instanceof String) {
            return descriptionObject.toString();
        }
        return null;
    }

    public void setMaxUsers(long userId, String maxUsers) {
        hashOps.put(userId + USER_DATA_PREFIX, "max_users", maxUsers);
    }

    public String getMaxUsers(long userId) {
        Object maxUsersObject = hashOps.get(userId + USER_DATA_PREFIX, "max_users");
        if (maxUsersObject instanceof String) {
            return maxUsersObject.toString();
        }
        return null;
    }

    public void setPic(long userId, String pic) {
        hashOps.put(userId + USER_DATA_PREFIX, "pic", pic);
    }

    public String getPic(long userId) {
        Object picObject = hashOps.get(userId + USER_DATA_PREFIX, "pic");
        if (picObject instanceof String) {
            return picObject.toString();
        }
        return null;
    }

    public void setTrainingDate(long userId, String date) {
        hashOps.put(userId + USER_DATA_PREFIX, "date", date);
    }

    public String getTrainingDate(long userId) {
        Object dateObject = hashOps.get(userId + USER_DATA_PREFIX, "date");
        if (dateObject instanceof String) {
            return dateObject.toString();
        }
        return null;
    }

    public void setStartTime(long userId, String startTime) {
        hashOps.put(userId + USER_DATA_PREFIX, "start_time", startTime);
    }

    public String getStartTime(long userId) {
        Object startTimeObject = hashOps.get(userId + USER_DATA_PREFIX, "start_time");
        if (startTimeObject instanceof String) {
            return startTimeObject.toString();
        }
        return null;
    }

    public void setEndTime(long userId, String endTime) {
        hashOps.put(userId + USER_DATA_PREFIX, "end_time", endTime);
    }

    public String getEndTime(long userId) {
        Object endTimeObject = hashOps.get(userId + USER_DATA_PREFIX, "end_time");
        if (endTimeObject instanceof String) {
            return endTimeObject.toString();
        }
        return null;
    }

    public void setLink(long userId, String link) {
        hashOps.put(userId + USER_DATA_PREFIX, "link", link);
    }

    public String getLink(long userId) {
        Object linkObject = hashOps.get(userId + USER_DATA_PREFIX, "link");
        if (linkObject instanceof String) {
            return linkObject.toString();
        }
        return null;
    }

    public void clearTemplate(long id) {
        redisTemplate.delete(List.of(id + "_user_data:"));

    }

}
