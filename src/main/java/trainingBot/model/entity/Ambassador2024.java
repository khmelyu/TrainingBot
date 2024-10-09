package trainingBot.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(name = "ambassador_2024")
public class Ambassador2024 {
    @Id
    private long id;
    private String team;
    private String test_1_answer;
    private String word_1_answer;
    private String letter_1_answer;
    private String media_1_answer;
    private Integer points;
}
