package trainingBot.dto;

import lombok.*;

@Getter
@Setter
@Builder
public class GalleryDto {
    private String gallery;
    private String galleryCity;
    private String galleryPhone;
    private String galleryMail;
    private String curatorName;
    private String curatorLastname;
    private String curatorPhone;
    private String managerName;
    private String managerLastname;
    private String managerPhone;
}
