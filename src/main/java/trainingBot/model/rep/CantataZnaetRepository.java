package trainingBot.model.rep;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import trainingBot.model.entity.CantataZnaet;

import java.util.List;

public interface CantataZnaetRepository extends JpaRepository<CantataZnaet, Long> {
    @Query("SELECT cz FROM cantata_znaet cz WHERE LOWER(cz.title) LIKE LOWER(CONCAT('%', REPLACE(:title, ' ', '%'), '%'))")
    List<CantataZnaet> findByTitleLike(@Param("title") String title);
}
