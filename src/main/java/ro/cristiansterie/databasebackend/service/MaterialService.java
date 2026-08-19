package ro.cristiansterie.databasebackend.service;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.cristiansterie.databasebackend.dto.MaterialDTO;
import ro.cristiansterie.databasebackend.repository.MaterialRepository;
import ro.cristiansterie.databasebackend.util.converter.models.MaterialModelConverter;

import java.util.Set;
import java.util.UUID;

@Service
public class MaterialService {
	private final MaterialRepository repo;
	private final MaterialModelConverter converter;

	public MaterialService(MaterialRepository repo, MaterialModelConverter converter) {
		this.repo = repo;
		this.converter = converter;
	}

	@Transactional(readOnly = true)
	public MaterialDTO findById(UUID id) {
		return converter.toDto(repo.findById(id)
		                           .orElse(null));
	}

	@Transactional(readOnly = true)
	public Set<MaterialDTO> findAll() {
		return converter.toDtoSet(repo.findAll());
	}

	@Transactional
	public MaterialDTO save(MaterialDTO dto) {
		return converter.toDto(repo.save(converter.toEntity(dto)));
	}

	@Transactional
	public MaterialDTO update(UUID id, MaterialDTO dto) {
		if (id == null || dto.id() == null || id.equals(dto.id())) {
			throw new IllegalArgumentException("Invalid ID: " + id);
		}

		var entity = repo.findById(id)
		                 .orElseThrow(() -> new EntityNotFoundException("Could not find material with ID: " + id));

		return converter.toDto(repo.save(converter.toEntity(dto)));
	}

	@Transactional
	public void delete(UUID id) {
		repo.deleteById(id);
	}
}
