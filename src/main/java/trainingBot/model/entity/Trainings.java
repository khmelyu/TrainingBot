package trainingBot.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;

import java.sql.Date;
import java.sql.Time;
import java.util.UUID;


@Setter
@Getter
@Entity(name = "trainings")
public class Trainings {
    @Id
    @GeneratedValue
    @UuidGenerator(style = UuidGenerator.Style.AUTO)
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

    public Trainings(UUID id, String name, String description, String city, Date date, Time start_time, String type, String creator, Time end_time, String category, int max_users, String pic, String link, boolean actual, boolean archive) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.city = city;
        this.date = date;
        this.start_time = start_time;
        this.type = type;
        this.creator = creator;
        this.end_time = end_time;
        this.category = category;
        this.max_users = max_users;
        this.pic = pic;
        this.link = link;
        this.actual = actual;
        this.archive = archive;
    }

    public Trainings() {

    }
}

