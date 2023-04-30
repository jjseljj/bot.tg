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

    //определяет запрос для поиска записи с типом AuthCode,
    // у которой связанная сущность UserCredentials имеет значение равное переданному параметру userCredentials,
    // и сортирует результаты по убыванию значения поля id.
    AuthCode findTopByUserCredentialsOrderByIdDesc(UserCredentials userCredentials);


    AuthCode findByAuthCodeAndUserCredentialsPhoneNumber(String code, String phoneNumber);

}

