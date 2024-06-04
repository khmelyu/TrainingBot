package trainingBot.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity(name = "marathon")
public class Marathon {
    @Id
    private long id;
    private String sex;
    private String training_time;
    private int time_zone;
    private int points;
    private boolean actual;
    private String feedback;
}
