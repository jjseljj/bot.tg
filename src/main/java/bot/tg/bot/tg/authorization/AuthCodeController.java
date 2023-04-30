package bot.tg.bot.tg.authorization;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Random;

// AuthCodeController отвечает за обработку запросов, связанных с кодом аутентификации
@RestController
@RequestMapping("/api/auth-code")
public class AuthCodeController {

    @Autowired
    private AuthCodeRepository authCodeRepository;

    @Autowired
    private UserCredentialsRepository userCredentialsRepository;

    @Autowired
    private TwilioService twilioService;


    // Метод для отправки кода подтверждения на номер телефона пользователя
    @PostMapping("/send-code")
    public ResponseEntity<Void> sendVerificationCode(@RequestParam("phoneNumber") String phoneNumber) {
        // Находим учетные данные пользователя по номеру телефона
        UserCredentials userCredentials = userCredentialsRepository.findByPhoneNumber(phoneNumber);
        if (userCredentials == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        // Генерируем случайный 6-значный код подтверждения
        String authCode = generateAuthCode();
        // Создаем новый объект AuthCode и сохраняем его в базе данных
        AuthCode newAuthCode = new AuthCode();
        newAuthCode.setCode(authCode);
        newAuthCode.setUserCredentials(userCredentials);
        authCodeRepository.save(newAuthCode);
        // Отправляем код подтверждения на номер телефона пользователя через сервис Twilio
        twilioService.sendVerificationCode(phoneNumber, authCode);
        return ResponseEntity.ok().build();
    }

    // Метод для проверки кода подтверждения
    @PostMapping("/verify-code")
    public ResponseEntity<Void> verifyAuthCode(@RequestParam("code") String code,
                                               @RequestParam("phoneNumber") String phoneNumber) {
        // Находим учетные данные пользователя по номеру телефона
        UserCredentials userCredentials = userCredentialsRepository.findByPhoneNumber(phoneNumber);
        if (userCredentials == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        // Находим последний сохраненный код подтверждения для указанных учетных данных пользователя
        AuthCode authCode = authCodeRepository.findTopByUserCredentialsOrderByIdDesc(userCredentials);
        if (authCode == null || !authCode.getCode().equals(code)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        // Сохраняем идентификатор кода подтверждения в объекте UserCredentials
        userCredentials.setAuthId(authCode.getId().toString());
        userCredentialsRepository.save(userCredentials);
        return ResponseEntity.ok().build();
    }


    // Метод для генерации случайного 6-значного кода подтверждения
    private String generateAuthCode() {
        Random random = new Random();
        int code = 100000 + random.nextInt(900000);
        return Integer.toString(code);
    }
}
