package bot.tg.bot.tg.authorization;

//Класс AuthCode представляет модель для таблицы хранения кода аутентификации в базе данных.
// Он содержит поля для хранения идентификатора кода аутентификации,
// самого кода и связь с объектом UserCredentials,
// который относится к этому коду аутентификации.

//Этот класс используется, когда происходит проверка введенного пользователем
// кода аутентификации в методе verifyAuthCode в контроллере.
// Если введенный пользователем код аутентификации соответствует
// последнему сохраненному коду аутентификации для указанных учетных данных
// пользователя, то authId сохраняется в соответствующем объекте UserCredentials.
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
@Entity(name = "auth_code")
@Data
@NoArgsConstructor
public class AuthCode {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "code")
    private String code;

    @Column(name = "auth_code")
    private String authCode;

    @OneToOne
    @JoinColumn(name = "user_credentials_id")
    private UserCredentials userCredentials;

        public UserCredentials getUserCredentials() {
        return userCredentials;
    }

    public void setUserCredentials(UserCredentials userCredentials) {
        this.userCredentials = userCredentials;
    }
}
/*
CREATE TABLE auth_code (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    auth_code VARCHAR(255) NOT NULL
);

ALTER TABLE auth_code ADD COLUMN user_credentials_id BIGINT;
ALTER TABLE auth_code ADD CONSTRAINT fk_user_credentials_id FOREIGN KEY (user_credentials_id) REFERENCES user_credentials(id);

 */


/*
Таблица AuthCode используется для сохранения кода аутентификации и связывания его с конкретным пользователем. Это помогает при проверке введенного пользователем кода, чтобы убедиться, что он действительно соответствует пользователю.

Таблица UserRegistrationImpl хранит информацию, которую пользователь вводит при регистрации, и может использоваться для создания новой записи в таблице UserCredentials, которая хранит конфиденциальные данные пользователя, такие как пароль.

Таблица UserCredentials хранит конфиденциальную информацию, такую как пароль пользователя, и связывается с пользователем через его телефонный номер или имя пользователя.
 */
/*
реализации авторизации в чат-боте на Java с использованием Maven,
нужно будет использовать какую-то форму базы данных для
 хранения учетных данных,
  также нужно будет создать API-интерфейс для регистрации
  новых пользователей и проверки существующих пользователей.

общий шаги, которые нужно выполнить:

    Создайте таблицу в базе данных для хранения учетных данных. В таблице должны быть поля, такие как имя пользователя, пароль и телефон.

    Создайте класс модели пользователя, который будет использоваться для получения и хранения данных пользователя.

    Создайте классы DAO, которые будут использоваться для взаимодействия с базой данных и получения/сохранения данных пользователя.

    Создайте методы API для регистрации новых пользователей и проверки существующих пользователей. Эти методы должны использовать классы DAO для взаимодействия с базой данных.

    Создайте метод генерации ссылки авторизации, который будет генерировать уникальный идентификатор для каждого пользователя и сохранять его в базе данных.

    Создайте интерфейс пользователя для регистрации. В интерфейсе пользователя должны быть поля для ввода имени, фамилии и номера телефона.

    Создайте метод для отправки кода подтверждения на указанный номер телефона.

    Создайте метод для проверки кода подтверждения и завершения регистрации.

    Используйте классы DAO и API-методы для сохранения данных пользователя и завершения процесса регистрации.

    В методе generateAuthLink создайте ссылку, которая будет содержать уникальный идентификатор пользователя, чтобы позволить ему авторизоваться в чат-боте.
 */