package bot.tg.bot.tg.authorization;


import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;



@Entity(name = "user_credentials")
@Data
@NoArgsConstructor
@Getter
@Setter
public class UserCredentials {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "phone_number", unique = true)
    private String phoneNumber;

    @Column(name = "password")
    private String password;

    @Column(name = "first_name")
    private String username;

    @Column(name = "auth_id", unique = true)
    private String authId;

    public UserCredentials(String phoneNumber, String password, String username, String authId) {
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.username = username;
        this.authId = authId;
    }

    public String getUsername() {
        return this.username;
    }

    public String getAuthId() {
        return this.authId;
    }
    public void setAuthId(String authId) {
        this.authId = authId;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getPhoneNumber() {
        return this.phoneNumber;
    }
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }


}
