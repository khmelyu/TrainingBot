package trainingBot.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;


@Setter
@Getter
@Entity(name = "trainings_list")
public class TrainingsList {
    @Id
    private int id;
    private String name;
    private String description;
    private String city;
    private String category;
    private int max_users;
    private String pic;
}
