package ro.cristiansterie.databasebackend.repository;

import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
abstract class AbstractCrudRepositoryTest<E> {
	@Autowired
	private EntityManager entityManager;
	@Autowired
	private JdbcTemplate jdbcTemplate;

	protected abstract JpaRepository<E, UUID> repository();

	protected abstract E newEntity();

	protected abstract E updateEntity(E entity);

	protected abstract UUID entityId(E entity);

	protected abstract void assertUpdated(E entity);

	@BeforeEach
	void addBanknoteThicknessColumnMissingFromLiquibaseSchema() {
		jdbcTemplate.execute("ALTER TABLE banknotes ADD COLUMN IF NOT EXISTS thickness NUMERIC");
	}

	@Test
	void savesReadsUpdatesListsAndDeletesEntity() {
		JpaRepository<E, UUID> repository = repository();
		UUID missingId = UUID.randomUUID();
		assertThat(repository.findById(missingId)).isEmpty();

		E saved = repository.save(newEntity());
		flushAndClear();
		UUID id = entityId(saved);
		assertThat(id).isNotNull();

		E found = repository.findById(id).orElseThrow();
		repository.save(updateEntity(found));
		flushAndClear();

		E reloaded = repository.findById(id).orElseThrow();
		assertUpdated(reloaded);
		assertThat(repository.findAll())
				.anySatisfy(entity -> assertThat(entityId(entity)).isEqualTo(id));

		repository.deleteById(id);
		flushAndClear();
		assertThat(repository.findById(id)).isEmpty();
		assertThat(repository.existsById(id)).isFalse();
	}

	@Test
	void returnsEmptyForUnknownId() {
		assertThat(repository().findById(UUID.randomUUID())).isEmpty();
	}

	private void flushAndClear() {
		entityManager.flush();
		entityManager.clear();
	}
}
