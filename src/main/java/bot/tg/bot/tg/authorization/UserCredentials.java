package bot.tg.bot.tg.authorization;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "user_credentials")
@Data
@NoArgsConstructor
public class UserCredentials {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    //@Column(name = "phone_number", unique = true)
    @Column(name = "phoneNumber", unique = true)
    private String phoneNumber;
    @Column(name = "password")
    private String password;

    //@Column(name = "first_name")
    @Column(name = "username")
    private String username;

    //@Column(name = "auth_id", unique = true)
    @Column(name = "authId", unique = true)
    private String authId;

    public UserCredentials(String phoneNumber, String password, String username, String authId) {
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.username = username;
        this.authId = authId;
    }
    @OneToOne(mappedBy = "userCredentials")
    private AuthCode authCode;

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


/*
CREATE TABLE IF NOT EXISTS user_credentials (
    id BIGSERIAL PRIMARY KEY,
    phone_number VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    first_name VARCHAR(255) NOT NULL,
    auth_id VARCHAR(255) UNIQUE NOT NULL
);

ALTER TABLE auth_code
ADD COLUMN user_credentials_id BIGINT REFERENCES user_credentials(id);


 */
