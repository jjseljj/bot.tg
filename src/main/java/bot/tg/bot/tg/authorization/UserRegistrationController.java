package bot.tg.bot.tg.authorization;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserRegistrationController {
    @Autowired
    private UserRegistrationRepository userRegistrationRepository;

    @Autowired
    private UserCredentialsRepository userCredentialsRepository;

    // метод для регистрации нового пользователя
    @PostMapping("/register")
    public ResponseEntity<UserRegistrationImpl> registerNewUser(@RequestBody UserRegistrationImpl userRegistration) {
        // проверяем, что такой номер телефона еще не зарегистрирован
        if (userRegistrationRepository.findByPhoneNumber(userRegistration.getPhoneNumber()).isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }

        // сохраняем данные о пользователе
        UserRegistrationImpl newUserRegistration = userRegistrationRepository.save(userRegistration);

        // создаем учетную запись для пользователя
        UserCredentials newUserCredentials = new UserCredentials(userRegistration.getPhoneNumber(),
                userRegistration.getPassword(),
                userRegistration.getUsername(),
                null);
        userCredentialsRepository.save(newUserCredentials);

        return ResponseEntity.status(HttpStatus.CREATED).body(newUserRegistration);
    }


    // метод для получения информации о пользователе по ID
    @GetMapping("/{id}")
    public ResponseEntity<UserRegistrationImpl> getUserById(@PathVariable Long id) {
        // проверяем, что пользователь с таким ID существует
        if (!userRegistrationRepository.findById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }

        // возвращаем информацию о пользователе
        return ResponseEntity.ok(userRegistrationRepository.findById(id).get());
    }

    // метод для получения информации о пользователе по номеру телефона
    @GetMapping("/phone/{phoneNumber}")
    public UserRegistrationImpl getUserByPhoneNumber(@PathVariable String phoneNumber) {
        // проверяем, что пользователь с таким номером телефона существует
        Optional<UserRegistrationImpl> userRegistrationOptional = userRegistrationRepository.findByPhoneNumber(phoneNumber);
        if (userRegistrationOptional.isPresent()) {
            return userRegistrationOptional.get();
        } else {
            throw new RuntimeException("User with phone number " + phoneNumber + " not found.");
        }
    }




}
