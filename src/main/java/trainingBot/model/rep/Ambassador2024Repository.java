package trainingBot.model.rep;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import trainingBot.model.entity.Ambassador2024;

import java.util.List;

@Repository
public interface Ambassador2024Repository extends JpaRepository<Ambassador2024, Long> {
    @Query("SELECT DISTINCT team FROM ambassador_2024 ORDER BY team")
    List<String> findAllDistinct();



}
