package trainingBot.model.rep;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import trainingBot.model.entity.Trainings;

import java.util.UUID;

@Repository
public interface TrainingsRepository extends JpaRepository<Trainings, UUID> {
}
