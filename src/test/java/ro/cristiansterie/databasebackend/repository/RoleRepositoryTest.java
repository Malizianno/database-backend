package ro.cristiansterie.databasebackend.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import ro.cristiansterie.databasebackend.model.RoleEntity;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class RoleRepositoryTest extends AbstractCrudRepositoryTest<RoleEntity> {
	@Autowired
	private RoleRepository roleRepository;

	@Override
	protected JpaRepository<RoleEntity, UUID> repository() {
		return roleRepository;
	}

	@Override
	protected RoleEntity newEntity() {
		return new RoleEntity(null, "TEST_" + UUID.randomUUID(), "Original role " + UUID.randomUUID());
	}

	@Override
	protected RoleEntity updateEntity(RoleEntity role) {
		role.setDescription("Updated role " + UUID.randomUUID());
		return role;
	}

	@Override
	protected UUID entityId(RoleEntity role) {
		return role.getId();
	}

	@Override
	protected void assertUpdated(RoleEntity role) {
		assertThat(role.getDescription()).startsWith("Updated role ");
	}
}
