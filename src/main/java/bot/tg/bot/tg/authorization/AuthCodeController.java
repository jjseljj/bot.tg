package bot.tg.bot.tg.authorization;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ThreadLocalRandom;

//
@RestController
@RequestMapping("/auth")
public class AuthCodeController {

    private final UserCredentialsRepository userCredentialsRepository;
    private final AuthCodeRepository authCodeRepository;
    private final TwilioService twilioService;

    public AuthCodeController(UserCredentialsRepository userCredentialsRepository, AuthCodeRepository authCodeRepository, TwilioService twilioService) {
        this.userCredentialsRepository = userCredentialsRepository;
        this.authCodeRepository = authCodeRepository;
        this.twilioService = twilioService;
    }

    // Метод для отправки кода подтверждения на номер телефона пользователя
    @PostMapping("/send-code")
    public ResponseEntity<String> sendAuthCode(@RequestBody UserRegistrationRepository userRegistration) {
        UserCredentials storedUserCredentials = userCredentialsRepository.findByPhoneNumber(userRegistration.getPhoneNunber());
        if (storedUserCredentials == null) {
            return ResponseEntity.badRequest().body("User with this phone number does not exist.");
        }

        // Генерируем и сохраняем новый код подтверждения
        String authCode = generateAuthCode();
        AuthCode newAuthCode = new AuthCode();
        newAuthCode.setAuthCode(authCode);
        newAuthCode.setUserCredentials(storedUserCredentials);
        authCodeRepository.save(newAuthCode);

        // Отправляем код подтверждения на номер телефона пользователя
        twilioService.sendVerificationCode(storedUserCredentials.getPhoneNumber(), authCode);

        return ResponseEntity.ok("Auth code sent successfully.");
    }

    // Метод для проверки кода подтверждения
    @PostMapping("/verify-code")
    public ResponseEntity<String> verifyAuthCode(@RequestBody VerificationCode verificationCode) {
        UserCredentials storedUserCredentials = userCredentialsRepository.findByPhoneNumber(verificationCode.getPhoneNumber());
        if (storedUserCredentials == null) {
            return ResponseEntity.badRequest().body("User with this phone number does not exist.");
        }

        AuthCode latestAuthCode = authCodeRepository.findTopByUserCredentialsOrderByCreatedAtDesc(storedUserCredentials);
        if (latestAuthCode == null || !latestAuthCode.getAuthCode().equals(verificationCode.getAuthCode())) {
            return ResponseEntity.badRequest().body("Invalid verification code.");
        }

        // Устанавливаем authId для пользователя
        storedUserCredentials.setAuthId(verificationCode.getAuthId());
        userCredentialsRepository.save(storedUserCredentials);

        return ResponseEntity.ok("Verification code verified successfully.");
    }

    // Метод для генерации случайного 6-значного кода подтверждения
    private String generateAuthCode() {
        int authCode = ThreadLocalRandom.current().nextInt(100000, 999999);
        return String.valueOf(authCode);
    }
}
