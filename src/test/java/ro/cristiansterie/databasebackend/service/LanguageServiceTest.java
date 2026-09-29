package ro.cristiansterie.databasebackend.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.jpa.repository.JpaRepository;
import ro.cristiansterie.databasebackend.dto.LanguageDTO;
import ro.cristiansterie.databasebackend.model.LanguageEntity;
import ro.cristiansterie.databasebackend.repository.LanguageRepository;
import ro.cristiansterie.databasebackend.util.converter.ModelConverter;
import ro.cristiansterie.databasebackend.util.converter.models.LanguageModelConverter;

import java.util.Set;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class LanguageServiceTest extends AbstractCrudServiceTest<LanguageEntity, LanguageDTO> {
	@Mock private LanguageRepository repository;
	@Mock private LanguageModelConverter converter;
	private LanguageEntity entity;
	private LanguageDTO dto;

	@BeforeEach
	void setUp() {
		entity = new LanguageEntity();
		dto = new LanguageDTO(ID, UUID.randomUUID(), "French");
	}

	@Override protected JpaRepository<LanguageEntity, UUID> repository() { return repository; }
	@Override protected ModelConverter<LanguageEntity, LanguageDTO> converter() { return converter; }
	@Override protected LanguageEntity entity() { return entity; }
	@Override protected LanguageDTO dto() { return dto; }
	@Override protected CrudOperations<LanguageDTO> service() {
		var service = new LanguageService(repository, converter);
		return new CrudOperations<>() {
			public LanguageDTO find(UUID id) { return service.findById(id); }
			public Set<LanguageDTO> findAll() { return service.findAll(); }
			public LanguageDTO save(LanguageDTO value) { return service.save(value); }
			public LanguageDTO update(UUID id, LanguageDTO value) { return service.update(id, value); }
			public void delete(UUID id) { service.delete(id); }
		};
	}
	@Override protected void assertUpdated(LanguageEntity updated) {
		assertThat(updated.getName()).isEqualTo(dto.name());
		assertThat(updated.getCountryId()).isEqualTo(dto.countryId());
	}
}
