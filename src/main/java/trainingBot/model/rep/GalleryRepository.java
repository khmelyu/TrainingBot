package trainingBot.model.rep;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import trainingBot.model.entity.Gallery;

@Repository
public interface GalleryRepository extends JpaRepository<Gallery, Long> {
}
