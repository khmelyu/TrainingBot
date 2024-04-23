package trainingBot.model.rep;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import trainingBot.model.entity.Trainings;

import java.util.List;
import java.util.UUID;

@Repository
public interface TrainingsRepository extends JpaRepository<Trainings, UUID> {
    @Query("SELECT new trainingBot.model.entity.Trainings(t.id, t.name, t.description, t.city, t.date, t.start_time, t.type, t.creator, t.end_time, t.category, t.max_users, t.pic, t.link, t.actual, t.archive) FROM trainings t WHERE t.creator = :creator")
    List<Trainings> findByCreator(@Param("creator") long id);

    @Query("SELECT DISTINCT t.category FROM trainings t WHERE t.city = :city AND t.actual = true AND (t.date > current_date OR (t.date = current_date AND t.start_time > current_time))")
    List<String> findByCity(@Param("city") String city);

    @Query("SELECT new trainingBot.model.entity.Trainings(t.id, t.name, t.description, t.city, t.date, t.start_time, t.type, t.creator, t.end_time, t.category, t.max_users, t.pic, t.link, t.actual, t.archive) FROM trainings t WHERE t.city = :city AND t.category = :category AND t.actual = true AND (t.date > current_date OR (t.date = current_date AND t.start_time > current_time))")
    List<Trainings> findByCityAndCategory(@Param("city") String city, @Param("category") String category);
    boolean existsByCategory(String category);
}
