package ro.cristiansterie.databasebackend.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import ro.cristiansterie.databasebackend.model.ImageEntity;
import ro.cristiansterie.databasebackend.util.enums.CollectionType;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class ImageRepositoryTest extends AbstractCrudRepositoryTest<ImageEntity> {
	@Autowired
	private ImageRepository imageRepository;

	@Override
	protected JpaRepository<ImageEntity, UUID> repository() {
		return imageRepository;
	}

	@Override
	protected ImageEntity newEntity() {
		ImageEntity image = new ImageEntity();
		image.setItemId(UUID.randomUUID());
		image.setItemType(CollectionType.BOOKS);
		image.setImageUrl("https://example.test/" + UUID.randomUUID());
		return image;
	}

	@Override
	protected ImageEntity updateEntity(ImageEntity image) {
		image.setImageUrl("https://example.test/updated");
		return image;
	}

	@Override
	protected UUID entityId(ImageEntity image) {
		return image.getId();
	}

	@Override
	protected void assertUpdated(ImageEntity image) {
		assertThat(image.getImageUrl()).isEqualTo("https://example.test/updated");
	}
}
