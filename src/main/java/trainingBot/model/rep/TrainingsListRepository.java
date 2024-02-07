package trainingBot.model.rep;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import trainingBot.model.entity.TrainingsList;


@Repository
public interface TrainingsListRepository extends JpaRepository<TrainingsList, Long> {
}