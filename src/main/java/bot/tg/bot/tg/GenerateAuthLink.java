package bot.tg.bot.tg;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Optional;
import java.util.UUID;

import bot.tg.bot.tg.authorization.AuthCode;
import bot.tg.bot.tg.authorization.UserCredentials;
import bot.tg.bot.tg.authorization.UserRegistrationRepository;

//Класс GenerateAuthLink предназначен для генерации ссылки на авторизацию пользователя в Телеграме.
public class GenerateAuthLink {
    // Кодировка для URL
    private static final String ENCODING = StandardCharsets.UTF_8.toString();

    // Длина генерируемого кода авторизации
    private static final int AUTH_CODE_LENGTH = 6;

    // Ссылка на репозиторий для работы с пользователями
    private final UserRegistrationRepository userRegistrationRepository;

    // Адрес вебхука
    private final String webhookUrl;

    public GenerateAuthLink(UserRegistrationRepository userRegistrationRepository, String webhookUrl) {
        this.userRegistrationRepository = userRegistrationRepository;
        this.webhookUrl = webhookUrl;
    }

    // Метод для генерации ссылки на авторизацию пользователя
    public String generateAuthLink(String phoneNumber) {

        // Получаем пользователя по номеру телефона
        Optional<UserCredentials> userCredentialsOptional = userRegistrationRepository.findByPhoneNumber(phoneNumber)
                .map(userRegistration -> new UserCredentials(userRegistration.getPhoneNumber(), userRegistration.getPassword(), userRegistration.getUsername(), null));


        // Если пользователь не найден, возвращаем null
        if (userCredentialsOptional.isEmpty()) {
            return null;
        }

        // Генерируем код авторизации
        String authCode = generateAuthCode(userCredentialsOptional.get());

        // Сохраняем код авторизации в базе данных
        AuthCode authCodeEntity = new AuthCode();
        authCodeEntity.setCode(authCode);
        authCodeEntity.setUserCredentials(userCredentialsOptional.get());


        userRegistrationRepository.save(authCodeEntity);
        // Кодируем номер телефона и код авторизации для использования в URL
        String encodedPhoneNumber = encodeString(phoneNumber);
        String encodedAuthCode = encodeString(authCode);

        // Формируем ссылку на авторизацию с помощью адреса вебхука и закодированными параметрами
        String authLink = webhookUrl + "/auth?phone=" + encodedPhoneNumber + "&auth_code=" + encodedAuthCode;

        // Возвращаем сгенерированную ссылку
        return authLink;
    }
    private String encodeString(String input) {
        try {
            return URLEncoder.encode(input, ENCODING);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("Error encoding string", e);
        }
    }

    // Метод для генерации случайного кода авторизации
    private String generateAuthCode(UserCredentials userCredentials) {
        // Генерируем случайный UUID
        UUID uuid = UUID.randomUUID();
        // Берем первые 6 символов из UUID и возвращаем как строку
        return uuid.toString().substring(0, AUTH_CODE_LENGTH);
    }
}
//класс использует номер телефона пользователя,
// которому нужно авторизоваться, чтобы получить информацию о пользователе из репозитория,
// затем генерирует случайный код авторизации и сохраняет его в базе данных.
// Затем он кодирует номер телефона и код авторизации и формирует ссылку на авторизацию
// с помощью адреса вебхука и закодированными параметрами.
// В итоге он возвращает сгенерированную ссылку.