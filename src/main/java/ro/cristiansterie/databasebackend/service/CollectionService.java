package ro.cristiansterie.databasebackend.service;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.cristiansterie.databasebackend.dto.CollectionDTO;
import ro.cristiansterie.databasebackend.repository.CollectionRepository;
import ro.cristiansterie.databasebackend.util.converter.models.CollectionModelConverter;

import java.util.Set;
import java.util.UUID;

@Service
public class CollectionService {
	private final CollectionRepository repo;
	private final CollectionModelConverter converter;

	public CollectionService(CollectionRepository repo, CollectionModelConverter converter) {
		this.repo = repo;
		this.converter = converter;
	}

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
		if (id == null || dto.id() == null || id.equals(dto.id())) {
			throw new IllegalArgumentException("Invalid ID: " + id);
		}

		var entity = repo.findById(id)
		                 .orElseThrow(() -> new EntityNotFoundException("Could not find collection with ID: " + id));

		return converter.toDto(repo.save(converter.toEntity(dto)));
	}

	@Transactional
	public void delete(UUID id) {
		repo.deleteById(id);
	}
}
