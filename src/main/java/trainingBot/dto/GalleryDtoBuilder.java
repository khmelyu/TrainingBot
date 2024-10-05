package trainingBot.dto;

import lombok.experimental.UtilityClass;
import trainingBot.model.entity.Gallery;
import trainingBot.model.entity.Users;

import java.util.Objects;

@UtilityClass
public class GalleryDtoBuilder {
    public GalleryDto createDto(Gallery gallery, Users curator, Users manager) {
        if (Objects.isNull(curator) && Objects.isNull(manager)) {
            return GalleryDto.builder()
                    .gallery(gallery.getGallery())
                    .galleryCity(gallery.getCity())
                    .galleryPhone(gallery.getPhone())
                    .galleryMail(gallery.getMail())
                    .build();
        }
        if (Objects.isNull(curator)) {
            return GalleryDto.builder()
                    .gallery(gallery.getGallery())
                    .galleryCity(gallery.getCity())
                    .galleryPhone(gallery.getPhone())
                    .galleryMail(gallery.getMail())
                    .managerName(manager.getName())
                    .managerLastname(manager.getLastname())
                    .managerPhone(manager.getPhone())
                    .build();
        }
        if (Objects.isNull(manager)) {
            return GalleryDto.builder()
                    .gallery(gallery.getGallery())
                    .galleryCity(gallery.getCity())
                    .galleryPhone(gallery.getPhone())
                    .galleryMail(gallery.getMail())
                    .curatorName(curator.getName())
                    .curatorLastname(curator.getLastname())
                    .curatorPhone(curator.getPhone())
                    .build();
        } else {
            return GalleryDto.builder()
                    .gallery(gallery.getGallery())
                    .galleryCity(gallery.getCity())
                    .galleryPhone(gallery.getPhone())
                    .galleryMail(gallery.getMail())
                    .curatorName(curator.getName())
                    .curatorLastname(curator.getLastname())
                    .curatorPhone(curator.getPhone())
                    .managerName(manager.getName())
                    .managerLastname(manager.getLastname())
                    .managerPhone(manager.getPhone())
                    .build();
        }
    }
}
