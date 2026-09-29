package ro.cristiansterie.databasebackend.service;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.data.jpa.repository.JpaRepository;
import ro.cristiansterie.databasebackend.util.converter.ModelConverter;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.function.Consumer;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

abstract class AbstractCrudServiceTest<E, D> {
	protected static final UUID ID = UUID.randomUUID();
	private static final UUID MISSING_ID = UUID.randomUUID();

	protected abstract JpaRepository<E, UUID> repository();

	protected abstract ModelConverter<E, D> converter();

	protected abstract E entity();

	protected abstract D dto();

	protected abstract CrudOperations<D> service();

	protected abstract void assertUpdated(E entity);

	@Test
	void coversCrudOperationsAndUpdateEdgeCases() {
		var repository = repository();
		var converter = converter();
		var entity = entity();
		var dto = dto();
		var mappedDtos = Set.of(dto);

		when(repository.findById(ID)).thenReturn(Optional.of(entity));
		when(repository.findById(MISSING_ID)).thenReturn(Optional.empty());
		when(repository.findAll()).thenReturn(List.of(entity));
		when(converter.toDto(entity)).thenReturn(dto);
		when(converter.toDtoSet(List.of(entity))).thenReturn(mappedDtos);
		when(converter.toEntity(dto)).thenReturn(entity);
		when(repository.save(entity)).thenReturn(entity);

		var service = service();
		assertThat(service.find(ID)).isEqualTo(dto);
		assertThat(service.find(MISSING_ID)).isNull();
		assertThat(service.findAll()).isEqualTo(mappedDtos);
		assertThat(service.save(dto)).isEqualTo(dto);
		assertThat(service.update(ID, dto)).isEqualTo(dto);
		assertUpdated(entity);
		assertThatThrownBy(() -> service.update(null, dto))
				.isInstanceOf(IllegalArgumentException.class);
		assertThatThrownBy(() -> service.update(ID, null))
				.isInstanceOf(IllegalArgumentException.class);
		assertThatThrownBy(() -> service.update(MISSING_ID, dto))
				.isInstanceOf(EntityNotFoundException.class);
		service.delete(ID);

		verify(repository, times(2)).findById(ID);
		verify(repository, times(2)).findById(MISSING_ID);
		verify(repository).findAll();
		verify(converter).toDtoSet(List.of(entity));
		verify(converter, times(3)).toDto(entity);
		verify(converter).toEntity(dto);
		verify(repository, times(2)).save(entity);
		verify(repository).deleteById(ID);
	}

	protected interface CrudOperations<D> {
		D find(UUID id);

		Set<D> findAll();

		D save(D dto);

		D update(UUID id, D dto);

		void delete(UUID id);
	}
}
