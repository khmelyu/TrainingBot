package trainingBot.model.rep;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import trainingBot.model.entity.Jumanji;
@Repository
public interface JumanjiRepository extends JpaRepository <Jumanji, Long> {
}
