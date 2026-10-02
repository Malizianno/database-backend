package ro.cristiansterie.databasebackend.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.cristiansterie.databasebackend.dto.CollectionDTO;
import ro.cristiansterie.databasebackend.repository.CollectionRepository;
import ro.cristiansterie.databasebackend.util.Validator;
import ro.cristiansterie.databasebackend.util.converter.models.CollectionModelConverter;

import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CollectionService {
	private final CollectionRepository repo;
	private final CollectionModelConverter converter;

	@Transactional(readOnly = true)
	public CollectionDTO findById(UUID id) {
		return converter.toDto(repo.findById(id)
		                           .orElse(null));
	}

	@Transactional(readOnly = true)
	public Set<CollectionDTO> findAll() {
		return converter.toDtoSet(repo.findAll());
	}

	@Transactional
	public CollectionDTO save(CollectionDTO dto) {
		return converter.toDto(repo.save(converter.toEntity(dto)));
	}

	@Transactional
	public CollectionDTO update(UUID id, CollectionDTO dto) {
		if (dto == null || !Validator.isUUIDValid(id))
			throw new IllegalArgumentException("Invalid ID: " + id);

		var entity = repo.findById(id)
		                 .orElseThrow(() -> new EntityNotFoundException("Could not find collection with ID: " + id));

		entity.setName(dto.name());
		entity.setDescription(dto.description());
		entity.setType(dto.type());
		entity.setUserId(dto.userId());

		return converter.toDto(repo.save(entity));
	}

	@Transactional
	public void delete(UUID id) {
		if (!Validator.isUUIDValid(id))
			throw new IllegalArgumentException("Invalid ID: " + id);

		if (!repo.existsById(id))
			throw new IllegalArgumentException("Could not find banknote with ID: " + id);

		repo.deleteById(id);
	}
}
