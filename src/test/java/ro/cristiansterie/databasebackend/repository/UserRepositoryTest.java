package ro.cristiansterie.databasebackend.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Example;
import org.springframework.data.repository.query.FluentQuery;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import ro.cristiansterie.databasebackend.model.RoleEntity;
import ro.cristiansterie.databasebackend.model.UserEntity;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class UserRepositoryTest {

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private RoleRepository roleRepository;

	@Test
	void testFindById() {
		// insert
		Set<RoleEntity> roles = new HashSet<>();
		roles.add(new RoleEntity(UUID.randomUUID(), "ADMIN", "can do everything"));
		UserEntity user = new UserEntity("admin", "12345", "admin@databaseproject.ro", roles);
		UserEntity saved = userRepository.save(user);

		// read
		UserEntity found = userRepository.findById(saved.getId())
		                                 .orElseThrow();

		// assert
		assertThat(found.getUsername()).isEqualTo(user.getUsername());
	}

	@Test
	void testFindByUsername() {
		// insert
		Set<RoleEntity> roles = new HashSet<>();
		var savedRole = roleRepository.findBy(Example.of(new RoleEntity(null, "ADMIN", null)), FluentQuery.FetchableFluentQuery::first)
		                              .orElse(null);
		roles.add(savedRole);
		UserEntity user = new UserEntity("admin-test", "12345", "admin@databaseproject", roles);
		UserEntity saved = userRepository.save(user);

		// read
		UserEntity found = userRepository.findByUsername(saved.getUsername())
		                                 .orElseThrow();

		// assert
		assertThat(found.getUsername()).isEqualTo(user.getUsername());
	}

	@Test
	void testFindAll() {
		// insert
		Set<RoleEntity> roles = new HashSet<>();
		var savedRole = roleRepository.findBy(Example.of(new RoleEntity(null, "ADMIN", null)), FluentQuery.FetchableFluentQuery::first)
		                              .orElse(null);
		roles.add(savedRole);
		UserEntity user1 = new UserEntity("admintest", "12345", "admin@databaseproject", roles);
		UserEntity user2 = new UserEntity("admintest2", "12345", "admin2@databaseproject", roles);

		var countBefore = userRepository.findAll()
		                                .size();
		List.of(user1, user2)
		    .forEach(user -> userRepository.save(user));

		// read
		List<UserEntity> found = userRepository.findAll();

		// assert
		assertThat(found.size()).isEqualTo(countBefore + 2);
		assertThat(found.stream()
		                .map(UserEntity::getUsername)
		                .anyMatch(u -> u.equals(user1.getUsername())));
	}

	@Test
	void testSave() {
		// insert/check
		Set<RoleEntity> roles = new HashSet<>();
		var savedRole = roleRepository.findBy(Example.of(new RoleEntity(null, "ADMIN", null)), FluentQuery.FetchableFluentQuery::first)
		                              .orElse(null);
		roles.add(savedRole);
		UserEntity user = new UserEntity("admintest", "12345", "admin@databaseproject", roles);
		var countBefore = userRepository.findAll()
		                                .size();

		// read/insert
		UserEntity saved = userRepository.save(user);

		// assert
		assertThat(userRepository.findAll()
		                         .size()).isEqualTo(countBefore + 1);
		assertThat(userRepository.findAll()
		                         .stream()
		                         .map(UserEntity::getUsername)
		                         .anyMatch(u -> u.equals(user.getUsername())));
	}

	@Test
	void testUpdate() {
		// insert
		Set<RoleEntity> roles = new HashSet<>();
		var savedRole = roleRepository.findBy(Example.of(new RoleEntity(null, "ADMIN", null)), FluentQuery.FetchableFluentQuery::first)
		                              .orElse(null);
		roles.add(savedRole);

		UserEntity user = new UserEntity("admintest", "12345", "admin@databaseproject", roles);
//		UserEntity testUser = new UserEntity(user.getUsername(), user.getPassword(), user.getEmail(), roles);
//		testUser.setId(user.getId());
		var countBeforeAdd = userRepository.findAll()
		                                   .size();
		UserEntity saved = userRepository.save(user);

		// read
		assertThat(userRepository.findAll()
		                         .size()).isEqualTo(countBeforeAdd + 1);
		saved.setUsername("updated");
//		userRepository.save(testUser);

		// assert
		assertThat(userRepository.findAll()
		                         .size()).isEqualTo(countBeforeAdd + 1);
		assertThat(userRepository.findAll()
		                         .stream()
		                         .map(UserEntity::getUsername)
		                         .anyMatch(u -> u.equals(saved.getUsername()))).isTrue();
	}

	@Test
	void testDelete() {
		// insert
		Set<RoleEntity> roles = new HashSet<>();
		var savedRole = roleRepository.findBy(Example.of(new RoleEntity(null, "ADMIN", null)), FluentQuery.FetchableFluentQuery::first)
		                              .orElse(null);
		roles.add(savedRole);

		UserEntity user = new UserEntity("admintest", "12345", "admin@databaseproject", roles);
		UserEntity saved = userRepository.save(user);
		var countBefore = userRepository.findAll()
		                                .size();

		// read
		userRepository.deleteById(saved.getId());

		// assert
		assertThat(userRepository.findAll()
		                         .size()).isEqualTo(countBefore - 1);
	}
}
