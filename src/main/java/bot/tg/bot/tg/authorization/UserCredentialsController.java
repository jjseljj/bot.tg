package bot.tg.bot.tg.authorization;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserCredentialsController {

    private final UserCredentialsRepository userCredentialsRepository;

    public UserCredentialsController(UserCredentialsRepository userCredentialsRepository) {
        this.userCredentialsRepository = userCredentialsRepository;
    }

    // Метод для регистрации нового пользователя
    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody UserCredentials userCredentials) {
        if (userCredentialsRepository.findByUsername(userCredentials.getUsername()) != null) {
            return ResponseEntity.badRequest().body("User with this username already exists.");
        }
        userCredentialsRepository.save(userCredentials);
        return ResponseEntity.ok("User registered successfully.");
    }

    // Метод для проверки существующего пользователя
    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@RequestBody UserCredentials userCredentials) {
        UserCredentials storedUserCredentials = userCredentialsRepository.findByUsername(userCredentials.getUsername());
        if (storedUserCredentials == null || !storedUserCredentials.getPassword().equals(userCredentials.getPassword())) {
            return ResponseEntity.badRequest().body("Invalid username or password.");
        }
        return ResponseEntity.ok("User logged in successfully.");
    }


}
