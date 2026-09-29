package ro.cristiansterie.databasebackend.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import ro.cristiansterie.databasebackend.model.LanguageEntity;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class LanguageRepositoryTest extends AbstractCrudRepositoryTest<LanguageEntity> {
	@Autowired
	private LanguageRepository languageRepository;

	@Override
	protected JpaRepository<LanguageEntity, UUID> repository() {
		return languageRepository;
	}

	@Override
	protected LanguageEntity newEntity() {
		LanguageEntity language = new LanguageEntity();
		language.setName("Repository test " + UUID.randomUUID());
		return language;
	}

	@Override
	protected LanguageEntity updateEntity(LanguageEntity language) {
		language.setName("Updated language");
		return language;
	}

	@Override
	protected UUID entityId(LanguageEntity language) {
		return language.getId();
	}

	@Override
	protected void assertUpdated(LanguageEntity language) {
		assertThat(language.getName()).isEqualTo("Updated language");
	}
}
