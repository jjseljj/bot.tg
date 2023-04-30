package bot.tg.bot.tg.authorization;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRegistrationRepository extends JpaRepository<UserRegistrationImpl, Long> {
    Optional<UserRegistrationImpl> findByPhoneNumber(String phoneNumber);
    Optional<UserRegistrationImpl> findById(Long id);
    UserRegistrationImpl save(UserRegistrationImpl userRegistration);
    AuthCode save(AuthCode authCode);
}
