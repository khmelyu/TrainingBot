package trainingBot.model.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;


@Setter
@Getter
@Entity(name = "Users")
public class User {
    @Id
    private Long id;
    private String username;


}
