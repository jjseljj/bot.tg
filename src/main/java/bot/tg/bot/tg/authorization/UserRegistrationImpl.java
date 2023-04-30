package bot.tg.bot.tg.authorization;

//Класс UserRegistrationImpl предназначен для того,
// чтобы хранить данные, которые вводит пользователь при регистрации.
// Затем, эти данные могут быть использованы для создания новой записи
// в таблице UserCredentials.

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

    @Entity(name = "user_registration")
    @Data
    @NoArgsConstructor
    @Getter
    @Setter
    public class UserRegistrationImpl{

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @Column(name = "phone_number", unique = true)
        private String phoneNumber;

        @Column(name = "password")
        private String password;

        @Column(name = "username")
        private String username;
}

/*
CREATE TABLE user_registration (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    phone_number VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    username VARCHAR(255) NOT NULL
);

 */