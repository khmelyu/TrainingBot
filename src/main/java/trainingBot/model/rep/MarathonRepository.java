package trainingBot.model.rep;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import trainingBot.model.entity.Marathon;

import java.util.List;

public interface MarathonRepository extends JpaRepository<Marathon, Long> {
    @Modifying
    @Query("update marathon set actual = false where id = :id")
    void abort(@Param("id") long id);

    List<Marathon> findAll();

    default int countAll() {
        return (int) count();
    }

}
