package trainingBot.model.rep;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import trainingBot.model.entity.Gallery;

import java.util.List;

@Repository
public interface GalleryRepository extends JpaRepository<Gallery, Long> {

    @Query("SELECT g FROM gallery g WHERE LOWER(g.gallery) LIKE LOWER(CONCAT('%', :text, '%'))")
    List<Gallery> searchGallery(@Param("text") String text, Pageable pageable);
}


