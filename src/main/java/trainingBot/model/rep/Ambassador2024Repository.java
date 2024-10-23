package trainingBot.model.rep;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import trainingBot.model.entity.Ambassador2024;
import trainingBot.model.entity.Users;

import java.util.List;

@Repository
public interface Ambassador2024Repository extends JpaRepository<Ambassador2024, Long> {
    @Query("SELECT DISTINCT team FROM ambassador_2024 ORDER BY team")
    List<String> findAllDistinct();

    @Query("SELECT new trainingBot.model.entity.Users(u.id, u.name, u.lastname, u.phone, u.city, u.gallery, u.rate, u.position, u.department, u.level, u.seniority,u.division,u.static_points,u.dynamic_points,u.training_points,u.coach,u.admin) FROM Users u JOIN ambassador_2024 am ON u.id = am.id WHERE am.team = :team")
    List<Users> findMembersByTeam(@Param("team") String team);

    @Query (value = "SELECT test_3_answer from ambassador_2024 where id =:id", nativeQuery = true)
    String teamAnswerTest(@Param("id") Long id);

    @Query (value = "SELECT word_3_answer from ambassador_2024 where id =:id", nativeQuery = true)
    String teamAnswerOneWord(@Param("id") Long id);

    @Query (value = "SELECT letter_3_answer from ambassador_2024 where id =:id", nativeQuery = true)
    String teamAnswerLetter(@Param("id") Long id);

    @Query (value = "SELECT media_3_answer from ambassador_2024 where id =:id", nativeQuery = true)
    String teamAnswerMedia(@Param("id") Long id);
}
