package ro.cristiansterie.databasebackend.service;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.cristiansterie.databasebackend.dto.LanguageDTO;
import ro.cristiansterie.databasebackend.repository.LanguageRepository;
import ro.cristiansterie.databasebackend.util.Validator;
import ro.cristiansterie.databasebackend.util.converter.models.LanguageModelConverter;

import java.util.Set;
import java.util.UUID;

@Service
public class LanguageService {
	private final LanguageRepository repo;
	private final LanguageModelConverter converter;

	public LanguageService(LanguageRepository repo, LanguageModelConverter converter) {
		this.repo = repo;
		this.converter = converter;
	}

	@Transactional(readOnly = true)
	public LanguageDTO findById(UUID id) {
		return converter.toDto(repo.findById(id)
		                           .orElse(null));
	}

	@Transactional(readOnly = true)
	public Set<LanguageDTO> findAll() {
		return converter.toDtoSet(repo.findAll());
	}

	@Transactional
	public LanguageDTO save(LanguageDTO dto) {
		return converter.toDto(repo.save(converter.toEntity(dto)));
	}

	@Transactional
	public LanguageDTO update(UUID id, LanguageDTO dto) {
		if (dto == null || !Validator.isUUIDValid(id)) {
			throw new IllegalArgumentException("Invalid ID: " + id);
		}

		var entity = repo.findById(id)
		                 .orElseThrow(() -> new EntityNotFoundException("Could not find language with ID: " + id));

		entity.setName(dto.name());
		entity.setCountryId(dto.countryId());

		return converter.toDto(repo.save(entity));
	}

	@Transactional
	public void delete(UUID id) {
		repo.deleteById(id);
	}
}
