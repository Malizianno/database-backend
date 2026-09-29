package ro.cristiansterie.databasebackend.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import ro.cristiansterie.databasebackend.model.CoinEntity;
import ro.cristiansterie.databasebackend.model.DenominationEntity;
import ro.cristiansterie.databasebackend.util.enums.CollectionItemConditionType;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class CoinRepositoryTest extends AbstractCrudRepositoryTest<CoinEntity> {
	@Autowired
	private CoinRepository coinRepository;
	@Autowired
	private DenominationRepository denominationRepository;

	@Override
	protected JpaRepository<CoinEntity, UUID> repository() {
		return coinRepository;
	}

	@Override
	protected CoinEntity newEntity() {
		CoinEntity coin = new CoinEntity();
		DenominationEntity denomination = new DenominationEntity();
		denomination.setTitle("Coin denomination " + UUID.randomUUID());
		coin.setDenominationId(denominationRepository.save(denomination).getId());
		coin.setCondition(CollectionItemConditionType.GOOD);
		coin.setYear(2000);
		coin.setDiameter(20.0);
		coin.setNumericValue(1);
		coin.setUnits(1);
		coin.setDescription("Original coin");
		return coin;
	}

	@Override
	protected CoinEntity updateEntity(CoinEntity coin) {
		coin.setDescription("Updated coin");
		return coin;
	}

	@Override
	protected UUID entityId(CoinEntity coin) {
		return coin.getId();
	}

	@Override
	protected void assertUpdated(CoinEntity coin) {
		assertThat(coin.getDescription()).isEqualTo("Updated coin");
	}
}
