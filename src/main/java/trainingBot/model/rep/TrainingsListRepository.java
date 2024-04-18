package trainingBot.model.rep;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import trainingBot.model.entity.TrainingsList;

import java.util.List;


@Repository
public interface TrainingsListRepository extends JpaRepository<TrainingsList, Long> {
    @Query("SELECT DISTINCT t.category FROM trainings_list t WHERE t.city = :city")
    List<String> findByCity(@Param("city") String city);

    @Query("SELECT new trainingBot.model.entity.TrainingsList(t.id, t.name, t.description, t.city, t.category, t.max_users, t.pic) FROM trainings_list t WHERE t.city = :city AND t.category = :category")
    List<TrainingsList> findByCityAndCategory(@Param("city") String city, @Param("category") String category);

    boolean existsByCategory(String category);


}