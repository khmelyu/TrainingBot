package trainingBot.model.rep;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import trainingBot.model.entity.Office;

import java.util.List;

@Repository
public interface OfficeRepository extends JpaRepository<Office, Long> {
    @Query("SELECT o FROM office o WHERE LOWER(o.department) LIKE LOWER(CONCAT('%', :text, '%')) OR LOWER(o.staffer) LIKE LOWER(CONCAT('%', :text, '%'))")
    List<Office> searchStaffer(@Param("text") String text, Pageable pageable);
}
