package trainingBot.model.rep;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import trainingBot.model.entity.Users;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<Users, Long> {

    @Query(value = "SELECT u.* FROM public.user u INNER JOIN gallery g ON u.id = g.curator WHERE g.id = :id", nativeQuery = true)
    Users findCuratorByGallery(@Param("id") Long id);

    @Query(value = "SELECT u.* FROM public.user u INNER JOIN gallery g ON u.id = g.manager WHERE g.id = :id", nativeQuery = true)
    Users findManagerByGallery(@Param("id") Long id);

    @Query(value = "SELECT DISTINCT u.city FROM public.user u WHERE u.department = 'Офис' AND u.division is not null", nativeQuery = true)
    List<String> findCitiesOffice ();

    @Query(value = "SELECT DISTINCT u.division FROM public.user u WHERE u.department = 'Офис' AND u.city = :city", nativeQuery = true)
    List<String> findDivisionsOnCity (@Param("city") String city);

    @Query(value = "SELECT DISTINCT u.* FROM public.user u WHERE u.department = 'Офис' AND u.city = :city AND u.division = :division ORDER BY u.lastname", nativeQuery = true)
    List<Users> findUsersByDivisionsAndCity (@Param("city") String city, @Param("division") String division);

    @Query(value = "SELECT CASE WHEN department IS NULL THEN true ELSE false END FROM \"user\" WHERE id = :id", nativeQuery = true)
    boolean departmentIsNull(@Param("id") Long id);
}

