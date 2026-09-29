package ro.cristiansterie.databasebackend.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.jpa.repository.JpaRepository;
import ro.cristiansterie.databasebackend.dto.CollectionDTO;
import ro.cristiansterie.databasebackend.model.CollectionEntity;
import ro.cristiansterie.databasebackend.repository.CollectionRepository;
import ro.cristiansterie.databasebackend.util.converter.ModelConverter;
import ro.cristiansterie.databasebackend.util.converter.models.CollectionModelConverter;
import ro.cristiansterie.databasebackend.util.enums.CollectionType;

import java.util.Set;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class CollectionServiceTest extends AbstractCrudServiceTest<CollectionEntity, CollectionDTO> {
	@Mock private CollectionRepository repository;
	@Mock private CollectionModelConverter converter;
	private CollectionEntity entity;
	private CollectionDTO dto;

	@BeforeEach
	void setUp() {
		entity = new CollectionEntity();
		dto = new CollectionDTO(ID, UUID.randomUUID(), "World coins", "Collected coins", CollectionType.COINS);
	}

	@Override protected JpaRepository<CollectionEntity, UUID> repository() { return repository; }
	@Override protected ModelConverter<CollectionEntity, CollectionDTO> converter() { return converter; }
	@Override protected CollectionEntity entity() { return entity; }
	@Override protected CollectionDTO dto() { return dto; }
	@Override protected CrudOperations<CollectionDTO> service() {
		var service = new CollectionService(repository, converter);
		return new CrudOperations<>() {
			public CollectionDTO find(UUID id) { return service.findById(id); }
			public Set<CollectionDTO> findAll() { return service.findAll(); }
			public CollectionDTO save(CollectionDTO value) { return service.save(value); }
			public CollectionDTO update(UUID id, CollectionDTO value) { return service.update(id, value); }
			public void delete(UUID id) { service.delete(id); }
		};
	}
	@Override protected void assertUpdated(CollectionEntity updated) {
		assertThat(updated.getUserId()).isEqualTo(dto.userId());
		assertThat(updated.getName()).isEqualTo(dto.name());
		assertThat(updated.getDescription()).isEqualTo(dto.description());
		assertThat(updated.getType()).isEqualTo(dto.type());
	}
}
