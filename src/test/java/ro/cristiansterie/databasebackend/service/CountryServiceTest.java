package ro.cristiansterie.databasebackend.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.jpa.repository.JpaRepository;
import ro.cristiansterie.databasebackend.dto.CountryDTO;
import ro.cristiansterie.databasebackend.model.CountryEntity;
import ro.cristiansterie.databasebackend.repository.CountryRepository;
import ro.cristiansterie.databasebackend.util.converter.ModelConverter;
import ro.cristiansterie.databasebackend.util.converter.models.CountryModelConverter;

import java.util.Set;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class CountryServiceTest extends AbstractCrudServiceTest<CountryEntity, CountryDTO> {
	@Mock private CountryRepository repository;
	@Mock private CountryModelConverter converter;
	private CountryEntity entity;
	private CountryDTO dto;

	@BeforeEach
	void setUp() {
		entity = new CountryEntity();
		dto = new CountryDTO(ID, "France", "Europe", "FR");
	}

	@Override protected JpaRepository<CountryEntity, UUID> repository() { return repository; }
	@Override protected ModelConverter<CountryEntity, CountryDTO> converter() { return converter; }
	@Override protected CountryEntity entity() { return entity; }
	@Override protected CountryDTO dto() { return dto; }
	@Override protected CrudOperations<CountryDTO> service() {
		var service = new CountryService(repository, converter);
		return new CrudOperations<>() {
			public CountryDTO find(UUID id) { return service.findById(id); }
			public Set<CountryDTO> findAll() { return service.findAll(); }
			public CountryDTO save(CountryDTO value) { return service.save(value); }
			public CountryDTO update(UUID id, CountryDTO value) { return service.update(id, value); }
			public void delete(UUID id) { service.delete(id); }
		};
	}
	@Override protected void assertUpdated(CountryEntity updated) {
		assertThat(updated.getName()).isEqualTo(dto.name());
		assertThat(updated.getContinent()).isEqualTo(dto.continent());
		assertThat(updated.getFlag()).isEqualTo(dto.flag());
	}
}
