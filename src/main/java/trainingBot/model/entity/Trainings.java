package trainingBot.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;
import java.sql.Time;
import java.util.UUID;


@Setter
@Getter
@Entity(name = "trainings")
public class Trainings {
    @Id
    private UUID id;
    private String name;
    private String description;
    private String city;
    private Date date;
    private Time start_time;
    private String type;
    private String creator;
    private Time end_time;
    private String category;
    private int max_users;
    private String pic;
    private String link;
    private boolean actual;
    private boolean archive;
}
