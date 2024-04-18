package trainingBot.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;


@Setter
@Getter
@Entity(name = "trainings_list")
public class TrainingsList {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private String description;
    private String city;
    private String category;
    private int max_users;
    private String pic;

    public TrainingsList() {

    }

    public TrainingsList(long id, String name, String description, String city, String category, int max_users, String pic) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.city = city;
        this.category = category;
        this.max_users = max_users;
        this.pic = pic;
    }
}
