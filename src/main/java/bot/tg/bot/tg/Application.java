package bot.tg.bot.tg;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class Application {
	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(Application.class, args);

		String botUsername = context.getEnvironment().getProperty("bot.username");
		String botToken = context.getEnvironment().getProperty("bot.token");

		TelegramBot bot = new TelegramBot(botToken, botUsername);
		bot.run();
		bot.runScheduler();
	}
}