package ro.cristiansterie.databasebackend.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.jpa.repository.JpaRepository;
import ro.cristiansterie.databasebackend.dto.MaterialDTO;
import ro.cristiansterie.databasebackend.model.MaterialEntity;
import ro.cristiansterie.databasebackend.repository.MaterialRepository;
import ro.cristiansterie.databasebackend.util.converter.ModelConverter;
import ro.cristiansterie.databasebackend.util.converter.models.MaterialModelConverter;

import java.util.Set;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class MaterialServiceTest extends AbstractCrudServiceTest<MaterialEntity, MaterialDTO> {
	@Mock private MaterialRepository repository;
	@Mock private MaterialModelConverter converter;
	private MaterialEntity entity;
	private MaterialDTO dto;

	@BeforeEach
	void setUp() {
		entity = new MaterialEntity();
		dto = new MaterialDTO(ID, "Silver");
	}

	@Override protected JpaRepository<MaterialEntity, UUID> repository() { return repository; }
	@Override protected ModelConverter<MaterialEntity, MaterialDTO> converter() { return converter; }
	@Override protected MaterialEntity entity() { return entity; }
	@Override protected MaterialDTO dto() { return dto; }
	@Override protected CrudOperations<MaterialDTO> service() {
		var service = new MaterialService(repository, converter);
		return new CrudOperations<>() {
			public MaterialDTO find(UUID id) { return service.findById(id); }
			public Set<MaterialDTO> findAll() { return service.findAll(); }
			public MaterialDTO save(MaterialDTO value) { return service.save(value); }
			public MaterialDTO update(UUID id, MaterialDTO value) { return service.update(id, value); }
			public void delete(UUID id) { service.delete(id); }
		};
	}
	@Override protected void assertUpdated(MaterialEntity updated) {
		assertThat(updated.getName()).isEqualTo(dto.name());
	}
}
