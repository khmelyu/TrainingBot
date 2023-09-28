package trainingBot.model.rep;


import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;
import trainingBot.model.entity.UserInfo;
@Repository
public interface UserInfoRepository extends JpaRepository<UserInfo, Long> {
}