package trainingBot.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity(name = "\"user\"")
public class Users {
    @Id
    private long id;
    private String name;
    private String lastname;
    private String phone;
    private String city;
    private String gallery;
    private String rate;
    private String position;
    private String department;
    private String level;
    private String seniority;
    private String division;
    @Getter
    private boolean coach;
    private boolean admin;

    public String userData() {
        if (this.department != null && this.department.equals("Офис")) {
            return "Имя: " + this.name + "\n" +
                    "Фамилия: " + this.lastname + "\n" +
                    "Телефон: " + this.phone + "\n" +
                    "Город: " + this.city + "\n" +
                    "Подразделение: " + this.department + "\n" +
                    "Отдел: " + this.division;
        } else if (this.department != null && this.department.equals("Галерея")) {
            return "Имя: " + this.name + "\n" +
                    "Фамилия: " + this.lastname + "\n" +
                    "Телефон: " + this.phone + "\n" +
                    "Город: " + this.city + "\n" +
                    "Подразделение: " + this.department + "\n" +
                    "Уровень: " + this.level + "\n" +
                    "Стаж: " + this.seniority + "\n" +
                    "Должность: " + this.position + "\n" +
                    "Галерея: " + this.gallery;
        } else {
            return "Имя: " + this.name + "\n" +
                    "Фамилия: " + this.lastname + "\n" +
                    "Телефон: " + this.phone + "\n" +
                    "Город: " + this.city + "\n" +
                    "Подразделение: " + this.department;
        }
    }
}