package trainingBot.model.rep;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import trainingBot.model.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query(value = "SELECT CASE WHEN department IS NULL THEN true ELSE false END FROM \"user\" WHERE id = :id", nativeQuery = true)
    boolean departmentIsNull(@Param("id") Long id);


}

