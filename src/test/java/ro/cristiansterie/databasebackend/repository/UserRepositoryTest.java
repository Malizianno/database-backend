package ro.cristiansterie.databasebackend.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import ro.cristiansterie.databasebackend.model.RoleEntity;
import ro.cristiansterie.databasebackend.model.UserEntity;

import java.util.Set;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class UserRepositoryTest extends AbstractCrudRepositoryTest<UserEntity> {
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private RoleRepository roleRepository;

	@Override
	protected JpaRepository<UserEntity, UUID> repository() {
		return userRepository;
	}

	@Override
	protected UserEntity newEntity() {
		RoleEntity role = roleRepository.save(
				new RoleEntity(null, "TEST_USER_" + UUID.randomUUID(), "Role " + UUID.randomUUID()));
		return new UserEntity("user-" + UUID.randomUUID(), "test-password",
		                      "user-" + UUID.randomUUID() + "@example.test", Set.of(role));
	}

	@Override
	protected UserEntity updateEntity(UserEntity user) {
		user.setEmail("updated-" + UUID.randomUUID() + "@example.test");
		return user;
	}

	@Override
	protected UUID entityId(UserEntity user) {
		return user.getId();
	}

	@Override
	protected void assertUpdated(UserEntity user) {
		assertThat(user.getEmail()).startsWith("updated-");
	}

	@Test
	void findsUsersByUsernameAndReturnsEmptyForUnknownUsername() {
		UserEntity user = userRepository.save(newEntity());
		assertThat(userRepository.findByUsername(user.getUsername())).get()
		                                                             .extracting(UserEntity::getId)
		                                                             .isEqualTo(user.getId());
		assertThat(userRepository.findByUsername("missing-" + UUID.randomUUID())).isEmpty();
	}
}
