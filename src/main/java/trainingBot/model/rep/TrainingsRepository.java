package trainingBot.model.rep;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import trainingBot.model.entity.Trainings;

import java.util.List;
import java.util.UUID;

@Repository
public interface TrainingsRepository extends JpaRepository<Trainings, UUID> {
    @Query("SELECT new trainingBot.model.entity.Trainings(t.id, t.name, t.description, t.city, t.date, t.start_time, t.type, t.creator, t.end_time, t.category, t.max_users, t.pic, t.link, t.actual, t.archive) FROM trainings t WHERE t.creator = :creator AND t.archive = false AND t.actual = true")
    List<Trainings> findByCreator(@Param("creator") long id);

    @Query("SELECT new trainingBot.model.entity.Trainings(t.id, t.name, t.description, t.city, t.date, t.start_time, t.type, t.creator, t.end_time, t.category, t.max_users, t.pic, t.link, t.actual, t.archive) FROM trainings t WHERE t.creator = :creator AND t.archive = true AND t.actual = true")
    List<Trainings> findByCreatorArchived(@Param("creator") long id);

    @Query("SELECT DISTINCT t.category FROM trainings t WHERE t.city = :city AND t.actual = true AND t.archive = false AND (t.date > current_date OR (t.date = current_date AND t.start_time > current_time))")
    List<String> findByCity(@Param("city") String city);

    @Query("SELECT new trainingBot.model.entity.Trainings(t.id, t.name, t.description, t.city, t.date, t.start_time, t.type, t.creator, t.end_time, t.category, t.max_users, t.pic, t.link, t.actual, t.archive) FROM trainings t WHERE t.city = :city AND t.category = :category AND t.actual = true AND t.archive = false AND (t.date > current_date OR (t.date = current_date AND t.start_time > current_time))")
    List<Trainings> findByCityAndCategory(@Param("city") String city, @Param("category") String category);

    @Modifying
    @Query("update trainings u set u.archive = true where u.id = :id")
    void archiveTraining(@Param("id") UUID id);

    @Modifying
    @Query("update trainings u set u.archive = false where u.id = :id")
    void UnArchiveTraining(@Param("id") UUID id);

    @Modifying
    @Query("update trainings u set u.actual = false where u.id = :id")
    void deleteTraining(@Param("id") UUID id);

    boolean existsByCategory(String category);
}