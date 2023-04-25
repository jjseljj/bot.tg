package bot.tg.bot.tg.authorization;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

//класс VerificationService для отправки кода подтверждения на указанный номер телефона,
// если без RESTful веб-сервис для отправки кода подтверждения через API
public class VerificationService {
    private static final String ACCOUNT_SID = "your_account_sid";
    private static final String AUTH_TOKEN = "your_auth_token";
    private static final String TWILIO_PHONE_NUMBER = "your_twilio_phone_number";

    // Метод для отправки кода подтверждения на указанный номер телефона
    public void sendVerificationCode(String phoneNumber, String code) {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);

        // Создание нового сообщения, содержащего код подтверждения, и отправка его на указанный номер телефона
        Message message = Message.creator(
                        new PhoneNumber(phoneNumber),
                        new PhoneNumber(TWILIO_PHONE_NUMBER),
                        "Your verification code is " + code)
                .create();

        // Вывод SID созданного сообщения для отладки
        System.out.println("Message SID: " + message.getSid());
    }
}
