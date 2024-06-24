package trainingBot.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity(name = "jumanji")
public class Jumanji {
    @Id
    private long id;
    private String team;
}
