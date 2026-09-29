package ro.cristiansterie.databasebackend.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.jpa.repository.JpaRepository;
import ro.cristiansterie.databasebackend.dto.DomainDTO;
import ro.cristiansterie.databasebackend.model.DomainEntity;
import ro.cristiansterie.databasebackend.repository.DomainRepository;
import ro.cristiansterie.databasebackend.util.converter.ModelConverter;
import ro.cristiansterie.databasebackend.util.converter.models.DomainModelConverter;

import java.util.Set;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class DomainServiceTest extends AbstractCrudServiceTest<DomainEntity, DomainDTO> {
	@Mock private DomainRepository repository;
	@Mock private DomainModelConverter converter;
	private DomainEntity entity;
	private DomainDTO dto;

	@BeforeEach
	void setUp() {
		entity = new DomainEntity();
		dto = new DomainDTO(ID, "Ancient", "Ancient history");
	}

	@Override protected JpaRepository<DomainEntity, UUID> repository() { return repository; }
	@Override protected ModelConverter<DomainEntity, DomainDTO> converter() { return converter; }
	@Override protected DomainEntity entity() { return entity; }
	@Override protected DomainDTO dto() { return dto; }
	@Override protected CrudOperations<DomainDTO> service() {
		var service = new DomainService(repository, converter);
		return new CrudOperations<>() {
			public DomainDTO find(UUID id) { return service.findById(id); }
			public Set<DomainDTO> findAll() { return service.findAll(); }
			public DomainDTO save(DomainDTO value) { return service.save(value); }
			public DomainDTO update(UUID id, DomainDTO value) { return service.update(id, value); }
			public void delete(UUID id) { service.delete(id); }
		};
	}
	@Override protected void assertUpdated(DomainEntity updated) {
		assertThat(updated.getName()).isEqualTo(dto.name());
		assertThat(updated.getDescription()).isEqualTo(dto.description());
	}
}
