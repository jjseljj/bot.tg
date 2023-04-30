package bot.tg.bot.tg;

import bot.tg.bot.tg.authorization.UserRegistrationRepository;
import org.json.JSONObject;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class TelegramBot extends TelegramLongPollingBot {
    private String botToken;
    private String botUsername;
    private UserRegistrationRepository userRegistrationRepository;
    private GenerateAuthLink generateAuthLink;
    public TelegramBot(String botToken, String botUsername, UserRegistrationRepository userRegistrationRepository, GenerateAuthLink generateAuthLink) {
        System.out.println("Bot token: " + botToken);
        System.out.println("Bot username: " + botUsername);
        this.botToken = botToken;
        this.botUsername = botUsername;
        this.userRegistrationRepository = userRegistrationRepository;
        this.generateAuthLink = generateAuthLink;
    }


    public TelegramBot(String botToken, String botUsername) {
        // в теле конструктора производите инициализацию объекта TelegramBot, используя переданные параметры
    }

    public TelegramBot() {
        this.botToken = " ";
        this.botUsername = " ";
    }
    // Реализация метода onUpdateReceived, который вызывается при получении нового обновления от Telegram
    @Override
    public void onUpdateReceived(Update update) {
        // Проверяем, содержит ли обновление сообщение и текст сообщения
        if (update.hasMessage() && update.getMessage().hasText()) {
            String messageText = update.getMessage().getText(); // Получаем текст сообщения
            Long chatId = update.getMessage().getChatId(); // Получаем ID чата, в котором было получено сообщение

            // Проверяем, равен ли текст сообщения "/start"
            if (messageText.equals("/start")) {
                sendAuthLink(chatId); // Если да, отправляем ссылку для авторизации пользователю
            }
        }
    }

    // Метод для отправки ссылки для авторизации пользователю
    private void sendAuthLink(Long chatId) {
        String authLink = generateAuthLink.generateAuthLink("    ");
        SendMessage message = new SendMessage();
        message.setChatId(chatId.toString());
        message.setText("Пройдите по ссылке для авторизации: " + authLink);

        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
/*

    @Autowired // инъектируем репозиторий
    private UserRegistrationRepository userRegistrationRepository;

    @Autowired // инъектируем репозиторий
    private AuthCodeRepository authCodeRepository;
    */

    // Метод для получения ID бота
  private String getBotId() {
      // Формируем ссылку на метод getMe
      String apiUrl = "https://api.telegram.org/bot" + botToken + "/";
      String method = "getMe";
      String url = apiUrl + method;

      // Отправляем запрос и получаем JSON-объект
      JSONObject json = null;
      try {
          URLConnection connection = new URL(url).openConnection();
          InputStream response = connection.getInputStream();
          BufferedReader reader = new BufferedReader(new InputStreamReader(response));
          String line = reader.readLine();
          json = new JSONObject(line);
      } catch (Exception e) {
          e.printStackTrace();
      }

      // Получаем id бота из JSON-объекта
      String botId = "";
      if (json != null && json.optBoolean("ok")) {
          JSONObject result = json.optJSONObject("result");
          if (result != null) {
              botId = result.optString("id");
          }
      }
      return botId;
  }

    // Реализация метода getBotUsername, возвращающего имя бота
    @Override
    public String getBotUsername() {
        return botUsername;
    }

    // Реализация метода getBotToken, возвращающего токен бота
    @Override
    public String getBotToken() {
        return botToken;
    }

    public void run() {
        TelegramBotsApi telegramBotsApi = null;
        try {
            telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
        try {
            telegramBotsApi.registerBot(this);
        } catch (TelegramApiException e) {
            System.err.println("Failed to register bot: " + e.getMessage());
            e.printStackTrace();
        }
    }
    public void runScheduler() {
        try {
            // Создаем объект Scheduler
            Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();

            // Запускаем Scheduler
            scheduler.start();

            // Создаем JobDetail для задачи, которую хотим запускать
            JobDetail jobDetail = JobBuilder.newJob(MyJob.class).withIdentity("myJob").build();

            // Создаем Trigger, который будет запускать задачу раз в минуту
            Trigger trigger = TriggerBuilder.newTrigger().withIdentity("myTrigger")
                    .withSchedule(CronScheduleBuilder.cronSchedule("0 * * * * ?")).build();

            // Регистрируем задачу и триггер в Scheduler
            scheduler.scheduleJob(jobDetail, trigger);
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }
    // Создаем класс, который будет выполняться в задаче
    public static class MyJob implements Job {
        public void execute(JobExecutionContext context) throws JobExecutionException {
            // Код, который нужно выполнить в задаче
            System.out.println("Job running!");
        }
    }
}

/*

    private void loadConfig() {
        Properties prop = new Properties();
        InputStream input = null;

        try {
            input = new FileInputStream("config.properties");
            prop.load(input);

            botToken = prop.getProperty("botToken");
            botUsername = prop.getProperty("botUsername");
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

 */
 /*
    private String generateAuthLink() {
        try {
            String encodedBotUsername = URLEncoder.encode(botUsername, StandardCharsets.UTF_8);
            String params = "bot_username=" + encodedBotUsername;
            String url = "https://telegram.org/auth/login?" + params;
            return url;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    */

/*
    private String generateAuthLink() {
        try {
            String encodedBotUsername = URLEncoder.encode(botUsername, StandardCharsets.UTF_8);
            String params = "bot_username=" + encodedBotUsername + "&auth_url=https://nigma.chat/auth";
            String url = "https://nigma.chat/oauth/authorize?" + params;
            return url;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    */


/*
    // Метод для генерации ссылки для авторизации
    private String generateAuthLink() {
        String botUsername = "XenodochialBotovskiBot";
        String botToken = "5688493107:AAHb2EJs-oNMCxYurZfPE2xmWwW5fi6G1Cs";
        String authUrl = "https://core.telegram.org/widgets/login";

        String deepLink = "https://telegram.me/" + botUsername + "?start=" + botToken + "&url=" + authUrl;

        return deepLink;
    }
*/
  /*
    private String generateAuthLink() {
        // Формируем ссылку на авторизацию
        String apiUrl = "https://api.telegram.org/bot" + botToken + "/";
        String method = "getMe"; // Метод getMe используется для получения информации о боте
        String url = apiUrl + method;

        // Отправляем запрос и получаем JSON-объект
        JSONObject json = null;
        try {
            URLConnection connection = new URL(url).openConnection();
            InputStream response = connection.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(response));
            String line = reader.readLine();
            json = new JSONObject(line);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Получаем id бота и формируем ссылку на авторизацию
        String botId = "";
        if (json != null && json.optBoolean("ok")) {
            JSONObject result = json.optJSONObject("result");
            if (result != null) {
                botId = result.optString("id");
            }
        }
        return "https://example.com/auth?telegram_id=" + botId;
    }

    */