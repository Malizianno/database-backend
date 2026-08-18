package ro.cristiansterie.databasebackend.service;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import ro.cristiansterie.databasebackend.dto.LanguageDTO;
import ro.cristiansterie.databasebackend.repository.LanguageRepository;
import ro.cristiansterie.databasebackend.util.converter.models.LanguageModelConverter;

import java.util.Set;

@Service
public class LanguageService {
	private final LanguageRepository repo;
	private final LanguageModelConverter converter;

	public LanguageService(LanguageRepository repo, LanguageModelConverter converter) {
		this.repo = repo;
		this.converter = converter;
	}

	public LanguageDTO findById(Long id) {
		return converter.toDto(repo.findById(id)
		                           .orElse(null));
	}

	public Set<LanguageDTO> findAll() {
		return converter.toDtoSet(repo.findAll());
	}

	public LanguageDTO save(LanguageDTO dto) {
		return converter.toDto(repo.save(converter.toEntity(dto)));
	}

	public LanguageDTO update(Long id, LanguageDTO dto) {
		if (id == null || dto.id() == null || id.equals(dto.id())) {
			throw new IllegalArgumentException("Invalid ID: " + id);
		}

		var entity = repo.findById(id)
		                 .orElseThrow(() -> new EntityNotFoundException("Could not find language with ID: " + id));

		return converter.toDto(repo.save(converter.toEntity(dto)));
	}

	public void delete(Long id) {
		repo.deleteById(id);
	}
}
