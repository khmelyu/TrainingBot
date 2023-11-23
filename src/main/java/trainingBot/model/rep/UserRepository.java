package trainingBot.model.rep;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import trainingBot.model.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}

