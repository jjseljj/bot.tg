package bot.tg.bot.tg.authorization;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserCredentialsRepository extends JpaRepository<UserCredentials, Long> {
    UserCredentials findByPhoneNumber(String phoneNumber);
    //UserCredentials findByUsername(String username);
    //UserCredentials findByLastName(String lastName);
}
