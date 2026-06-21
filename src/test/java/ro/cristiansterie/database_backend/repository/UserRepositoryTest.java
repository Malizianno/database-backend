package ro.cristiansterie.database_backend.repository;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import ro.cristiansterie.database_backend.model.RoleEntity;
import ro.cristiansterie.database_backend.model.UserEntity;

import java.util.HashSet;
import java.util.Set;

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
        Set<RoleEntity> roles = new HashSet<>();
        roles.add(new RoleEntity(1L, "ADMIN", "can do everything"));
        UserEntity user = new UserEntity("admin", "12345", "admin@databaseproject.ro", roles);
        UserEntity saved = userRepository.save(user);

        // read
        UserEntity found = userRepository.findById(saved.getId()).orElseThrow();

        assertThat(found.getUsername()).isEqualTo("admin");
    }
}
