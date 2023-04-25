package bot.tg.bot.tg.authorization;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserRegistrationController {
    @Autowired
    private UserRegistrationRepository userRegistrationRepository;

    // метод для регистрации нового пользователя
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody UserRegistrationImpl request) {
        try {
            // создаем новый объект пользователя и сохраняем его в базе данных
            UserRegistrationImpl user = new UserRegistrationImpl(request.getPhoneNumber(), request.getPassword(), request.getUsername());
            userRegistrationRepository.save(user);
            return ResponseEntity.ok("User registered successfully!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error registering user.");
        }
    }

    // метод для получения информации о пользователе по ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable("id") Long id) {
        Optional<UserRegistrationImpl> user = userRegistrationRepository.findById(id);
        if (user.isPresent()) {
            return ResponseEntity.ok(user.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // метод для получения информации о пользователе по номеру телефона
    @GetMapping("/phone/{phoneNumber}")
    public ResponseEntity<?> getUserByPhoneNumber(@PathVariable("phoneNumber") String phoneNumber) {
        Optional<UserRegistrationImpl> username = userRegistrationRepository.findByPhoneNumber(phoneNumber);
        if (username.isPresent()) {
            return ResponseEntity.ok(username.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }


}
