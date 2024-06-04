package trainingBot.model.rep;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import trainingBot.model.entity.Marathon;
import trainingBot.model.entity.User;

import java.util.List;

public interface MarathonRepository extends JpaRepository<Marathon, Long> {
    @Modifying
    @Query("update marathon set actual = false where id = :id")
    void abort(@Param("id") long id);

    List<Marathon> findAll();

    @Modifying
    @Query("update marathon m set m.feedback = :feedback where m.id = :id")
    void saveMarathonFeedback(@Param("feedback") String feedback, @Param("id") long id);

}
