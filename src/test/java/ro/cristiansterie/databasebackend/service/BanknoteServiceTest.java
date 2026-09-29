package ro.cristiansterie.databasebackend.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.jpa.repository.JpaRepository;
import ro.cristiansterie.databasebackend.dto.BanknoteDTO;
import ro.cristiansterie.databasebackend.model.BanknoteEntity;
import ro.cristiansterie.databasebackend.repository.BanknoteRepository;
import ro.cristiansterie.databasebackend.util.converter.ModelConverter;
import ro.cristiansterie.databasebackend.util.converter.models.BanknoteModelConverter;
import ro.cristiansterie.databasebackend.util.enums.CollectionItemConditionType;

import java.util.Set;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class BanknoteServiceTest extends AbstractCrudServiceTest<BanknoteEntity, BanknoteDTO> {
	@Mock private BanknoteRepository repository;
	@Mock private BanknoteModelConverter converter;
	private BanknoteEntity entity;
	private BanknoteDTO dto;

	@BeforeEach
	void setUp() {
		entity = new BanknoteEntity();
		dto = new BanknoteDTO(ID, UUID.randomUUID(), UUID.randomUUID(), UUID.randomUUID(),
				CollectionItemConditionType.VERY_GOOD, 1995, 12.5, 0.2, 6.0,
				"Description", "https://note.test", 10, 1996, 2);
	}

	@Override protected JpaRepository<BanknoteEntity, UUID> repository() { return repository; }
	@Override protected ModelConverter<BanknoteEntity, BanknoteDTO> converter() { return converter; }
	@Override protected BanknoteEntity entity() { return entity; }
	@Override protected BanknoteDTO dto() { return dto; }
	@Override protected CrudOperations<BanknoteDTO> service() {
		var service = new BanknoteService(repository, converter);
		return new CrudOperations<>() {
			public BanknoteDTO find(UUID id) { return service.findById(id); }
			public Set<BanknoteDTO> findAll() { return service.findAll(); }
			public BanknoteDTO save(BanknoteDTO value) { return service.save(value); }
			public BanknoteDTO update(UUID id, BanknoteDTO value) { return service.update(id, value); }
			public void delete(UUID id) { service.delete(id); }
		};
	}
	@Override protected void assertUpdated(BanknoteEntity updated) {
		assertThat(updated.getCollectionId()).isEqualTo(dto.collectionId());
		assertThat(updated.getMaterialId()).isEqualTo(dto.materialId());
		assertThat(updated.getDenominationId()).isEqualTo(dto.denominationId());
		assertThat(updated.getCondition()).isEqualTo(dto.condition());
		assertThat(updated.getYear()).isEqualTo(dto.year());
		assertThat(updated.getLength()).isEqualTo(dto.length());
		assertThat(updated.getThickness()).isEqualTo(dto.thickness());
		assertThat(updated.getWidth()).isEqualTo(dto.width());
		assertThat(updated.getDescription()).isEqualTo(dto.description());
		assertThat(updated.getLink()).isEqualTo(dto.link());
		assertThat(updated.getNumericValue()).isEqualTo(dto.numericValue());
		assertThat(updated.getExtraYear()).isEqualTo(dto.extraYear());
		assertThat(updated.getUnits()).isEqualTo(dto.units());
	}
}
