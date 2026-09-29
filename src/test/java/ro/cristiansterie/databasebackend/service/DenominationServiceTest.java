package ro.cristiansterie.databasebackend.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.jpa.repository.JpaRepository;
import ro.cristiansterie.databasebackend.dto.DenominationDTO;
import ro.cristiansterie.databasebackend.model.DenominationEntity;
import ro.cristiansterie.databasebackend.repository.DenominationRepository;
import ro.cristiansterie.databasebackend.util.converter.ModelConverter;
import ro.cristiansterie.databasebackend.util.converter.models.DenominationModelConverter;

import java.util.Set;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class DenominationServiceTest extends AbstractCrudServiceTest<DenominationEntity, DenominationDTO> {
	@Mock private DenominationRepository repository;
	@Mock private DenominationModelConverter converter;
	private DenominationEntity entity;
	private DenominationDTO dto;

	@BeforeEach
	void setUp() {
		entity = new DenominationEntity();
		dto = new DenominationDTO(ID, UUID.randomUUID(), "10 francs");
	}

	@Override protected JpaRepository<DenominationEntity, UUID> repository() { return repository; }
	@Override protected ModelConverter<DenominationEntity, DenominationDTO> converter() { return converter; }
	@Override protected DenominationEntity entity() { return entity; }
	@Override protected DenominationDTO dto() { return dto; }
	@Override protected CrudOperations<DenominationDTO> service() {
		var service = new DenominationService(repository, converter);
		return new CrudOperations<>() {
			public DenominationDTO find(UUID id) { return service.findById(id); }
			public Set<DenominationDTO> findAll() { return service.findAll(); }
			public DenominationDTO save(DenominationDTO value) { return service.save(value); }
			public DenominationDTO update(UUID id, DenominationDTO value) { return service.update(id, value); }
			public void delete(UUID id) { service.delete(id); }
		};
	}
	@Override protected void assertUpdated(DenominationEntity updated) {
		assertThat(updated.getTitle()).isEqualTo(dto.title());
		assertThat(updated.getCountryId()).isEqualTo(dto.countryId());
	}
}
