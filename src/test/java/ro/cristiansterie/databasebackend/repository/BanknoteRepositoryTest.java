package ro.cristiansterie.databasebackend.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import ro.cristiansterie.databasebackend.model.BanknoteEntity;
import ro.cristiansterie.databasebackend.model.DenominationEntity;
import ro.cristiansterie.databasebackend.util.enums.CollectionItemConditionType;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class BanknoteRepositoryTest extends AbstractCrudRepositoryTest<BanknoteEntity> {
	@Autowired
	private BanknoteRepository banknoteRepository;
	@Autowired
	private DenominationRepository denominationRepository;

	@Override
	protected JpaRepository<BanknoteEntity, UUID> repository() {
		return banknoteRepository;
	}

	@Override
	protected BanknoteEntity newEntity() {
		BanknoteEntity banknote = new BanknoteEntity();
		DenominationEntity denomination = new DenominationEntity();
		denomination.setTitle("Banknote denomination " + UUID.randomUUID());
		banknote.setDenominationId(denominationRepository.save(denomination).getId());
		banknote.setCondition(CollectionItemConditionType.GOOD);
		banknote.setYear(2000);
		banknote.setLength(120.0);
		banknote.setWidth(60.0);
		banknote.setThickness(1.0);
		banknote.setNumericValue(1);
		banknote.setUnits(1);
		banknote.setDescription("Original banknote");
		return banknote;
	}

	@Override
	protected BanknoteEntity updateEntity(BanknoteEntity banknote) {
		banknote.setDescription("Updated banknote");
		return banknote;
	}

	@Override
	protected UUID entityId(BanknoteEntity banknote) {
		return banknote.getId();
	}

	@Override
	protected void assertUpdated(BanknoteEntity banknote) {
		assertThat(banknote.getDescription()).isEqualTo("Updated banknote");
	}
}
