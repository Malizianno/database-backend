package ro.cristiansterie.databasebackend.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.cristiansterie.databasebackend.dto.DomainDTO;
import ro.cristiansterie.databasebackend.repository.DomainRepository;
import ro.cristiansterie.databasebackend.util.Validator;
import ro.cristiansterie.databasebackend.util.converter.models.DomainModelConverter;

import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DomainService {
	private final DomainRepository repo;
	private final DomainModelConverter converter;

	@Transactional(readOnly = true)
	public DomainDTO findById(UUID id) {
		return converter.toDto(repo.findById(id)
		                           .orElse(null));
	}

	@Transactional(readOnly = true)
	public Set<DomainDTO> findAll() {
		return converter.toDtoSet(repo.findAll());
	}

	@Transactional
	public DomainDTO save(DomainDTO dto) {
		return converter.toDto(repo.save(converter.toEntity(dto)));
	}

	@Transactional
	public DomainDTO update(UUID id, DomainDTO dto) {
		if (dto == null || !Validator.isUUIDValid(id))
			throw new IllegalArgumentException("Invalid ID: " + id);

		var entity = repo.findById(id)
		                 .orElseThrow(() -> new EntityNotFoundException("Could not find domain with ID: " + id));

		entity.setDescription(dto.description());
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
