package bot.tg.bot.tg.authorization;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

//сервисом для генерации ссылок авторизации и связан с процессом аутентификации пользователей
@Service
public class AuthService {

    private final UserCredentialsRepository userCredentialsRepository;

    @Autowired
    public AuthService(UserCredentialsRepository userCredentialsRepository) {
        this.userCredentialsRepository = userCredentialsRepository;
    }

    public String generateAuthLink(String phoneNumber) {
        UserCredentials userCredentials = userCredentialsRepository.findByPhoneNumber(phoneNumber);
        if (userCredentials == null) {
            // если пользователя с таким номером телефона нет, то не генерируем ссылку
            return null;
        }
        // генерируем уникальный идентификатор для авторизации
        String authId = UUID.randomUUID().toString();
        userCredentials.setAuthId(authId);
        userCredentialsRepository.save(userCredentials);
        // возвращаем ссылку с авторизационным идентификатором
        return "https://example.com/auth/" + authId;
    }

}
