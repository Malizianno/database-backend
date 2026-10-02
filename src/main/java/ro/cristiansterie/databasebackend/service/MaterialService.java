package ro.cristiansterie.databasebackend.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.cristiansterie.databasebackend.dto.MaterialDTO;
import ro.cristiansterie.databasebackend.repository.MaterialRepository;
import ro.cristiansterie.databasebackend.util.Validator;
import ro.cristiansterie.databasebackend.util.converter.models.MaterialModelConverter;

import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MaterialService {
	private final MaterialRepository repo;
	private final MaterialModelConverter converter;

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
		if (dto == null || !Validator.isUUIDValid(id))
			throw new IllegalArgumentException("Invalid ID: " + id);

		var entity = repo.findById(id)
		                 .orElseThrow(() -> new EntityNotFoundException("Could not find material with ID: " + id));

		entity.setName(dto.name());

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
