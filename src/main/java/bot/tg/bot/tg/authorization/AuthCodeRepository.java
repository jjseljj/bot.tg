package bot.tg.bot.tg.authorization;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

//AuthCodeRepository расширяет интерфейс JpaRepository,
// предоставляемый Spring Data JPA. Он содержит ряд методов для выполнения различных операций
// с базой данных, таких как поиск, добавление, удаление и обновление.
// Метод findByCodeAndUserCredentialsPhoneNumber ищет код подтверждения по указанному коду
// и номеру телефона пользователя.
@Repository
public interface AuthCodeRepository extends JpaRepository<AuthCode, Long> {
    AuthCode findByCodeAndUserCredentialsPhoneNumber(String code, String phoneNumber);
    AuthCode findTopByUserCredentialsOrderByCreatedAtDesc(UserCredentials userCredentials);

    AuthCode save(AuthCode authCode);
}

