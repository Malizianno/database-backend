package ro.cristiansterie.databasebackend.service;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.cristiansterie.databasebackend.dto.DenominationDTO;
import ro.cristiansterie.databasebackend.repository.DenominationRepository;
import ro.cristiansterie.databasebackend.util.Validator;
import ro.cristiansterie.databasebackend.util.converter.models.DenominationModelConverter;

import java.util.Set;
import java.util.UUID;

@Service
public class DenominationService {
	private final DenominationRepository repo;
	private final DenominationModelConverter converter;

	public DenominationService(DenominationRepository repo, DenominationModelConverter converter) {
		this.repo = repo;
		this.converter = converter;
	}

	@Transactional(readOnly = true)
	public DenominationDTO findById(UUID id) {
		return converter.toDto(repo.findById(id)
		                           .orElse(null));
	}

	@Transactional(readOnly = true)
	public Set<DenominationDTO> findAll() {
		return converter.toDtoSet(repo.findAll());
	}

	@Transactional
	public DenominationDTO save(DenominationDTO dto) {
		return converter.toDto(repo.save(converter.toEntity(dto)));
	}

	@Transactional
	public DenominationDTO update(UUID id, DenominationDTO dto) {
		if (dto == null || !Validator.isUUIDValid(id)) {
			throw new IllegalArgumentException("Invalid ID: " + id);
		}

		var entity = repo.findById(id)
		                 .orElseThrow(() -> new EntityNotFoundException("Could not find denomination with ID: " + id));

		entity.setTitle(dto.title());
		entity.setCountryId(dto.countryId());

		return converter.toDto(repo.save(entity));
	}

	@Transactional
	public void delete(UUID id) {
		repo.deleteById(id);
	}
}
