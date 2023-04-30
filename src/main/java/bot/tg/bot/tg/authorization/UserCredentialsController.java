package bot.tg.bot.tg.authorization;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("/user-credentials")
public class UserCredentialsController {
    private UserCredentialsRepository userCredentialsRepository;
    @Autowired
    public UserCredentialsController(UserCredentialsRepository userCredentialsRepository) {
        this.userCredentialsRepository = userCredentialsRepository;
    }
    // Метод для регистрации нового пользователя
    @PostMapping("/register")
    public ResponseEntity<UserCredentials> registerUser(@RequestBody UserCredentials userCredentials) {
        if (userCredentialsRepository.findByPhoneNumber(userCredentials.getPhoneNumber()) != null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        UserCredentials savedUser = userCredentialsRepository.save(userCredentials);
        return ResponseEntity.ok(savedUser);
    }
    // Метод для проверки существующего пользователя
    @PostMapping("/login")
    public ResponseEntity<UserCredentials> loginUser(@RequestParam("phoneNumber") String phoneNumber,
                                                     @RequestParam("password") String password) {
        UserCredentials userCredentials = userCredentialsRepository.findByPhoneNumber(phoneNumber);
        if (userCredentials == null || !userCredentials.getPassword().equals(password)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.ok(userCredentials);
    }
}

