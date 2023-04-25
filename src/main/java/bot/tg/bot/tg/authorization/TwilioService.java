package bot.tg.bot.tg.authorization;


//класс является сервисом для отправки SMS сообщений через Twilio.
// Он содержит метод sendVerificationCode, который принимает номер телефона и код подтверждения
// в качестве аргументов, и использует Twilio API для отправки сообщения на указанный номер телефона.
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class TwilioService {

    @Value("${twilio.account.sid}")
    private String accountSid;

    @Value("${twilio.auth.token}")
    private String authToken;

    @Value("${twilio.phone.number}")
    private String phoneNumber;

    // Метод для отправки кода подтверждения на номер телефона пользователя
    public void sendVerificationCode(String toPhoneNumber, String authCode) {
        Twilio.init(accountSid, authToken);
        Message message = Message.creator(
                        new PhoneNumber(toPhoneNumber),
                        new PhoneNumber(phoneNumber),
                        "Your verification code is " + authCode)
                .create();
    }
}

