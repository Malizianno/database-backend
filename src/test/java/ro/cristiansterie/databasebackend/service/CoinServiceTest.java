package ro.cristiansterie.databasebackend.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.jpa.repository.JpaRepository;
import ro.cristiansterie.databasebackend.dto.CoinDTO;
import ro.cristiansterie.databasebackend.model.CoinEntity;
import ro.cristiansterie.databasebackend.repository.CoinRepository;
import ro.cristiansterie.databasebackend.util.converter.ModelConverter;
import ro.cristiansterie.databasebackend.util.converter.models.CoinModelConverter;
import ro.cristiansterie.databasebackend.util.enums.CollectionItemConditionType;

import java.util.Set;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class CoinServiceTest extends AbstractCrudServiceTest<CoinEntity, CoinDTO> {
	@Mock private CoinRepository repository;
	@Mock private CoinModelConverter converter;
	private CoinEntity entity;
	private CoinDTO dto;

	@BeforeEach
	void setUp() {
		entity = new CoinEntity();
		dto = new CoinDTO(ID, UUID.randomUUID(), UUID.randomUUID(), UUID.randomUUID(),
				CollectionItemConditionType.GOOD, 2001, 2.5, "Description", "https://coin.test", 5, 2002, 3);
	}

	@Override protected JpaRepository<CoinEntity, UUID> repository() { return repository; }
	@Override protected ModelConverter<CoinEntity, CoinDTO> converter() { return converter; }
	@Override protected CoinEntity entity() { return entity; }
	@Override protected CoinDTO dto() { return dto; }
	@Override protected CrudOperations<CoinDTO> service() {
		var service = new CoinService(repository, converter);
		return new CrudOperations<>() {
			public CoinDTO find(UUID id) { return service.findById(id); }
			public Set<CoinDTO> findAll() { return service.findAll(); }
			public CoinDTO save(CoinDTO value) { return service.save(value); }
			public CoinDTO update(UUID id, CoinDTO value) { return service.update(id, value); }
			public void delete(UUID id) { service.delete(id); }
		};
	}
	@Override protected void assertUpdated(CoinEntity updated) {
		assertThat(updated.getCollectionId()).isEqualTo(dto.collectionId());
		assertThat(updated.getMaterialId()).isEqualTo(dto.materialId());
		assertThat(updated.getDenominationId()).isEqualTo(dto.denominationId());
		assertThat(updated.getCondition()).isEqualTo(dto.condition());
		assertThat(updated.getYear()).isEqualTo(dto.year());
		assertThat(updated.getDiameter()).isEqualTo(dto.diameter());
		assertThat(updated.getDescription()).isEqualTo(dto.description());
		assertThat(updated.getLink()).isEqualTo(dto.link());
		assertThat(updated.getNumericValue()).isEqualTo(dto.numericValue());
		assertThat(updated.getExtraYear()).isEqualTo(dto.extraYear());
		assertThat(updated.getUnits()).isEqualTo(dto.units());
	}
}
