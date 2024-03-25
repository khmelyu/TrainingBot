package trainingBot.model.rep;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import trainingBot.model.entity.UsersToTrainings;
@Repository
public interface UsersToTrainingsRepository extends JpaRepository<UsersToTrainings, Long> {

}

