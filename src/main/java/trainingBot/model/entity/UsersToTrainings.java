package trainingBot.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Setter
@Getter
@Entity(name = "users_to_trainings")
public class UsersToTrainings {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "training_id")
    private Trainings trainings;

    private boolean actual;
    private Timestamp signup_time;
    private Timestamp abort_time;
    private boolean presence;
    private boolean waiting_list;
    private String feedback;

}
