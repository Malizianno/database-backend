package ro.cristiansterie.databasebackend.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import ro.cristiansterie.databasebackend.model.DomainEntity;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class DomainRepositoryTest extends AbstractCrudRepositoryTest<DomainEntity> {
	@Autowired
	private DomainRepository domainRepository;

	@Override
	protected JpaRepository<DomainEntity, UUID> repository() {
		return domainRepository;
	}

	@Override
	protected DomainEntity newEntity() {
		DomainEntity domain = new DomainEntity();
		domain.setName("Repository test " + UUID.randomUUID());
		domain.setDescription("Original domain");
		return domain;
	}

	@Override
	protected DomainEntity updateEntity(DomainEntity domain) {
		domain.setDescription("Updated domain");
		return domain;
	}

	@Override
	protected UUID entityId(DomainEntity domain) {
		return domain.getId();
	}

	@Override
	protected void assertUpdated(DomainEntity domain) {
		assertThat(domain.getDescription()).isEqualTo("Updated domain");
	}
}
