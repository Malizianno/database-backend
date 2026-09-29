package ro.cristiansterie.databasebackend.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.jpa.repository.JpaRepository;
import ro.cristiansterie.databasebackend.dto.ImageDTO;
import ro.cristiansterie.databasebackend.model.ImageEntity;
import ro.cristiansterie.databasebackend.repository.ImageRepository;
import ro.cristiansterie.databasebackend.util.converter.ModelConverter;
import ro.cristiansterie.databasebackend.util.converter.models.ImageModelConverter;
import ro.cristiansterie.databasebackend.util.enums.CollectionType;

import java.util.Set;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class ImageServiceTest extends AbstractCrudServiceTest<ImageEntity, ImageDTO> {
	@Mock
	private ImageRepository repository;
	@Mock
	private ImageModelConverter converter;
	private ImageEntity entity;
	private ImageDTO dto;

	@BeforeEach
	void setUp() {
		entity = new ImageEntity();
		dto = new ImageDTO(ID, UUID.randomUUID(), CollectionType.BOOKS, "https://image.test/book.png");
	}

	@Override
	protected JpaRepository<ImageEntity, UUID> repository() {
		return repository;
	}

	@Override
	protected ModelConverter<ImageEntity, ImageDTO> converter() {
		return converter;
	}

	@Override
	protected ImageEntity entity() {
		return entity;
	}

	@Override
	protected ImageDTO dto() {
		return dto;
	}

	@Override
	protected CrudOperations<ImageDTO> service() {
		var service = new ImageService(repository, converter);
		return new CrudOperations<>() {
			public ImageDTO find(UUID id) {
				return service.findById(id);
			}

			public Set<ImageDTO> findAll() {
				return service.findAll();
			}

			public ImageDTO save(ImageDTO value) {
				return service.save(value);
			}

			public ImageDTO update(UUID id, ImageDTO value) {
				return service.update(id, value);
			}

			public void delete(UUID id) {
				service.delete(id);
			}
		};
	}

	@Override
	protected void assertUpdated(ImageEntity updated) {
		assertThat(updated.getItemId()).isEqualTo(dto.itemId());
		assertThat(updated.getItemType()).isEqualTo(dto.itemType());
		assertThat(updated.getImageUrl()).isEqualTo(dto.imageUrl());
	}
}
