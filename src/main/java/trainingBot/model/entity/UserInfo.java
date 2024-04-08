package trainingBot.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.Objects;


@Setter
@Getter
@Entity(name = "user_info")
public class UserInfo {
    @Id
    private long id;
    private String name;
    private String lastname;
    private String username;
    private Timestamp last_update;
    private long update_count;

    public UserInfo() {
        this.update_count = 1; // Установка значения по умолчанию
    }

    public boolean equalsUserInfo(String firstName, String lastName, String username) {
        return Objects.equals(this.name, firstName)
                && Objects.equals(this.lastname, lastName)
                && Objects.equals(this.username, username);
    }
}
