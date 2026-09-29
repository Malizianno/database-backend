package ro.cristiansterie.databasebackend.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import ro.cristiansterie.databasebackend.model.MaterialEntity;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class MaterialRepositoryTest extends AbstractCrudRepositoryTest<MaterialEntity> {
	@Autowired
	private MaterialRepository materialRepository;

	@Override
	protected JpaRepository<MaterialEntity, UUID> repository() {
		return materialRepository;
	}

	@Override
	protected MaterialEntity newEntity() {
		MaterialEntity material = new MaterialEntity();
		material.setName("Repository test " + UUID.randomUUID());
		return material;
	}

	@Override
	protected MaterialEntity updateEntity(MaterialEntity material) {
		material.setName("Updated material");
		return material;
	}

	@Override
	protected UUID entityId(MaterialEntity material) {
		return material.getId();
	}

	@Override
	protected void assertUpdated(MaterialEntity material) {
		assertThat(material.getName()).isEqualTo("Updated material");
	}
}
