package trainingBot.model.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.sql.Timestamp;
import java.util.Objects;


@Setter
@Getter
@Entity(name = "user_info")
public class UserInfo {
    @Id
    private Long id;
    private String name;
    private String lastname;
    private String username;
    private Timestamp last_update;

    public boolean equalsUserInfo(String firstName, String lastName, String username) {
        return Objects.equals(this.name, firstName)
                && Objects.equals(this.lastname, lastName)
                && Objects.equals(this.username, username);
    }
}
