package trainingBot.model.rep;

import org.springframework.data.repository.CrudRepository;
import trainingBot.model.entity.User;

public interface UserRepository extends CrudRepository<User, Long> {
}
