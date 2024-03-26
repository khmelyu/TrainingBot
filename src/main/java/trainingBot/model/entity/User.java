package trainingBot.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity(name = "\"user\"")
public class User {
    @Id
    private long id;
    private String name;
    private String lastname;
    private String phone;
    private String city;
    private String gallery;
    private String rate;
    @Getter
    private boolean coach;
    private boolean admin;

    public String userData() {
        return "Имя: " + this.name + "\n" +
                "Фамилия: " + this.lastname + "\n" +
                "Телефон: " + this.phone + "\n" +
                "Город: " + this.city + "\n" +
                "Галерея: " + this.gallery + "\n" +
                "Ставка: " + this.rate + "\n";
    }
}