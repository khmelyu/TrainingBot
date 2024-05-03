package trainingBot.model.rep;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import trainingBot.model.entity.Office;

@Repository
public interface OfficeRepository extends JpaRepository<Office, Long> {
}
