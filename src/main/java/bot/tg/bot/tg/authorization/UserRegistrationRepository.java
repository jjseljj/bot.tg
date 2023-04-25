package bot.tg.bot.tg.authorization;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

public interface UserRegistrationRepository extends JpaRepository<UserRegistrationImpl, Long> {

    // метод для поиска пользователя по номеру телефона
    Optional<UserRegistrationImpl> findByPhoneNumber(String phoneNumber);
    Optional<UserRegistrationImpl> findById(Long id);


}
