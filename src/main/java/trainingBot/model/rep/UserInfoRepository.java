package trainingBot.model.rep;

import org.springframework.data.repository.CrudRepository;
import trainingBot.model.entity.UserInfo;

public interface UserInfoRepository extends CrudRepository<UserInfo, Long> {
}
