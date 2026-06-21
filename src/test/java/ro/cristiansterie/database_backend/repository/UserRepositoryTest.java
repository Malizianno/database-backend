package ro.cristiansterie.database_backend.repository;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import ro.cristiansterie.database_backend.model.User;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    @Transactional
    void testPostgresConnectionAndInsert() {
        // insert
        User user = new User("admin", "admin@databaseproject.ro");
        User saved = userRepository.save(user);

        // read
        User found = userRepository.findById(saved.getId()).orElseThrow();

        assertThat(found.getUsername()).isEqualTo("admin");
    }
}
