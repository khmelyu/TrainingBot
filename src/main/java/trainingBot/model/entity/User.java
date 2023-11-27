package trainingBot.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity (name = "\"user\"")
public class User {
    @Id
    private Long id;
    private String name;
    private String lastname;
    private String phone;
    private String city;
    private String gallery;
    private String rate;
    private boolean coach;
    private boolean admin;
}