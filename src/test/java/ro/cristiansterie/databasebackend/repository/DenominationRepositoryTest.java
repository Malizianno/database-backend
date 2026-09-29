package ro.cristiansterie.databasebackend.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import ro.cristiansterie.databasebackend.model.DenominationEntity;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class DenominationRepositoryTest extends AbstractCrudRepositoryTest<DenominationEntity> {
	@Autowired
	private DenominationRepository denominationRepository;

	@Override
	protected JpaRepository<DenominationEntity, UUID> repository() {
		return denominationRepository;
	}

	@Override
	protected DenominationEntity newEntity() {
		DenominationEntity denomination = new DenominationEntity();
		denomination.setTitle("Repository test " + UUID.randomUUID());
		return denomination;
	}

	@Override
	protected DenominationEntity updateEntity(DenominationEntity denomination) {
		denomination.setTitle("Updated denomination");
		return denomination;
	}

	@Override
	protected UUID entityId(DenominationEntity denomination) {
		return denomination.getId();
	}

	@Override
	protected void assertUpdated(DenominationEntity denomination) {
		assertThat(denomination.getTitle()).isEqualTo("Updated denomination");
	}
}
