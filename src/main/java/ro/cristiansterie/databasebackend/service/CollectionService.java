package ro.cristiansterie.databasebackend.service;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import ro.cristiansterie.databasebackend.dto.CollectionDTO;
import ro.cristiansterie.databasebackend.repository.CollectionRepository;
import ro.cristiansterie.databasebackend.util.converter.models.CollectionModelConverter;

import java.util.Set;

@Service
public class CollectionService {
	private final CollectionRepository repo;
	private final CollectionModelConverter converter;

	public CollectionService(CollectionRepository repo, CollectionModelConverter converter) {
		this.repo = repo;
		this.converter = converter;
	}

	public CollectionDTO findById(Long id) {
		return converter.toDto(repo.findById(id)
		                           .orElse(null));
	}

	public Set<CollectionDTO> findAll() {
		return converter.toDtoSet(repo.findAll());
	}

	public CollectionDTO save(CollectionDTO dto) {
		return converter.toDto(repo.save(converter.toEntity(dto)));
	}

	public CollectionDTO update(Long id, CollectionDTO dto) {
		if (id == null || dto.id() == null || id.equals(dto.id())) {
			throw new IllegalArgumentException("Invalid ID: " + id);
		}

		var entity = repo.findById(id)
		                 .orElseThrow(() -> new EntityNotFoundException("Could not find collection with ID: " + id));

		return converter.toDto(repo.save(converter.toEntity(dto)));
	}

	public void delete(Long id) {
		repo.deleteById(id);
	}
}
