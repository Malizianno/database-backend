package ro.cristiansterie.databasebackend.repository;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import ro.cristiansterie.databasebackend.model.RoleEntity;
import ro.cristiansterie.databasebackend.model.UserEntity;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;
	@Autowired
	private RoleRepository roleRepository;

    @Test
    @Transactional
    void testFindById() {
        // insert
        Set<RoleEntity> roles = new HashSet<>();
        roles.add(new RoleEntity(UUID.randomUUID(), "ADMIN", "can do everything"));
        UserEntity user = new UserEntity("admin", "12345", "admin@databaseproject.ro", roles);
        UserEntity saved = userRepository.save(user);

        // read
        UserEntity found = userRepository.findById(saved.getId()).orElseThrow();

		// assert
        assertThat(found.getUsername()).isEqualTo(user.getUsername());
    }

	@Test
	@Transactional
	void testFindByUsername() {
		// insert
		Set<RoleEntity> roles = new HashSet<>();
		roles.add(new RoleEntity(UUID.randomUUID(), "ADMIN", "can do everything"));
		UserEntity user = new UserEntity("admin", "12345", "admin@databaseproject", roles);
		UserEntity saved = userRepository.save(user);

		// read
		UserEntity found = userRepository.findByUsername(saved.getUsername()).orElseThrow();

		// assert
		assertThat(found.getUsername()).isEqualTo(user.getUsername());
	}

	@Test
	@Transactional
	void testFindAll() {
		// insert
		Set<RoleEntity> roles = new HashSet<>();
		roles.add(new RoleEntity(UUID.randomUUID(), "ADMIN", "can do everything"));
		UserEntity user1 = new UserEntity("admin", "12345", "admin@databaseproject", roles);
		UserEntity user2 = new UserEntity("admin2", "12345", "admin2@databaseproject", roles);
		UserEntity saved1 = userRepository.save(user1);
		UserEntity saved2 = userRepository.save(user2);

		// read
		List<UserEntity> found = userRepository.findAll();

		// assert
		assertThat(found.size()).isEqualTo(2);
		assertThat(found.get(0).getUsername()).isEqualTo(user1.getUsername());
	}

	@Test
	@Transactional
	void testSave() {
		// insert/check
		Set<RoleEntity> roles = new HashSet<>();
		roles.add(new RoleEntity(UUID.randomUUID(), "ADMIN", "can do everything"));
		UserEntity user = new UserEntity("admin", "12345", "admin@databaseproject", roles);
		assertThat(userRepository.findAll().size()).isEqualTo(0);

		// read/insert
		UserEntity saved = userRepository.save(user);

		// assert
		assertThat(userRepository.findAll().size()).isEqualTo(1);
		assertThat(userRepository.findAll().get(0).getUsername()).isEqualTo(user.getUsername());
	}

	@Test
	@Transactional
	void testUpdate() {
		// insert
		Set<RoleEntity> roles = new HashSet<>();
		RoleEntity role = new RoleEntity(null, "ADMIN", "can do everything");

		roleRepository.save(role);
		roles.add(role);

		UserEntity user = new UserEntity("admin", "12345", "admin@databaseproject", roles);
		UserEntity testUser = new UserEntity(user.getUsername(), user.getPassword(), user.getEmail(), roles);
		testUser.setId(user.getId());
		UserEntity saved = userRepository.save(user);

		// read
		assertThat(userRepository.findAll().size()).isEqualTo(1);
		saved.setUsername("updated");
//		userRepository.save(testUser);

		// assert
		assertThat(userRepository.findAll().size()).isEqualTo(1);
		assertThat(userRepository.findAll().get(0).getUsername()).isNotEqualTo(testUser.getUsername());
		assertThat(userRepository.findAll().get(0).getUsername()).isEqualTo(saved.getUsername());
	}

	@Test
	@Transactional
	void testDelete() {
		// insert
		Set<RoleEntity> roles = new HashSet<>();
		roles.add(new RoleEntity(UUID.randomUUID(), "ADMIN", "can do everything"));
		UserEntity user = new UserEntity("admin", "12345", "admin@databaseproject", roles);
		UserEntity saved = userRepository.save(user);
		assertThat(userRepository.findAll().size()).isEqualTo(1);

		// read
		userRepository.deleteById(saved.getId());

		// assert
		assertThat(userRepository.findAll().size()).isEqualTo(0);
	}
}
