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
    private Long id;
    private String name;
    private String lastname;
    private String phone;
    private String city;
    private String gallery;
    private String rate;
    private boolean coach;
    private boolean admin;

    public String userData() {
        StringBuilder userInfo = new StringBuilder();
        userInfo.append("Имя: ").append(this.name).append("\n");
        userInfo.append("Фамилия: ").append(this.lastname).append("\n");
        userInfo.append("Телефон: ").append(this.phone).append("\n");
        userInfo.append("Город: ").append(this.city).append("\n");
        userInfo.append("Галерея: ").append(this.gallery).append("\n");
        userInfo.append("Ставка: ").append(this.rate).append("\n");
        return userInfo.toString();
    }

}