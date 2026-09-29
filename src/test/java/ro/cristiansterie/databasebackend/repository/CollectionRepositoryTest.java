package ro.cristiansterie.databasebackend.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import ro.cristiansterie.databasebackend.model.CollectionEntity;
import ro.cristiansterie.databasebackend.util.enums.CollectionType;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class CollectionRepositoryTest extends AbstractCrudRepositoryTest<CollectionEntity> {
	@Autowired
	private CollectionRepository collectionRepository;

	@Override
	protected JpaRepository<CollectionEntity, UUID> repository() {
		return collectionRepository;
	}

	@Override
	protected CollectionEntity newEntity() {
		CollectionEntity collection = new CollectionEntity();
		collection.setName("Repository test " + UUID.randomUUID());
		collection.setType(CollectionType.BOOKS);
		collection.setDescription("Original collection");
		return collection;
	}

	@Override
	protected CollectionEntity updateEntity(CollectionEntity collection) {
		collection.setDescription("Updated collection");
		return collection;
	}

	@Override
	protected UUID entityId(CollectionEntity collection) {
		return collection.getId();
	}

	@Override
	protected void assertUpdated(CollectionEntity collection) {
		assertThat(collection.getDescription()).isEqualTo("Updated collection");
	}
}
