package ro.cristiansterie.databasebackend.service;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.cristiansterie.databasebackend.dto.CountryDTO;
import ro.cristiansterie.databasebackend.repository.CountryRepository;
import ro.cristiansterie.databasebackend.util.converter.models.CountryModelConverter;

import java.util.Set;

@Service
public class CountryService {
	private final CountryRepository repo;
	private final CountryModelConverter converter;

	public CountryService(CountryRepository repo, CountryModelConverter converter) {
		this.repo = repo;
		this.converter = converter;
	}

	@Transactional(readOnly = true)
	public CountryDTO findById(Long id) {
		return converter.toDto(repo.findById(id)
		                           .orElse(null));
	}

	@Transactional(readOnly = true)
	public Set<CountryDTO> findAll() {
		return converter.toDtoSet(repo.findAll());
	}

	@Transactional
	public CountryDTO save(CountryDTO dto) {
		return converter.toDto(repo.save(converter.toEntity(dto)));
	}

	@Transactional
	public CountryDTO update(Long id, CountryDTO dto) {
		if (id == null || dto.id() == null || id.equals(dto.id())) {
			throw new IllegalArgumentException("Invalid ID: " + id);
		}

		var entity = repo.findById(id)
		                 .orElseThrow(() -> new EntityNotFoundException("Could not find country with ID: " + id));

		return converter.toDto(repo.save(converter.toEntity(dto)));
	}

	@Transactional
	public void delete(Long id) {
		repo.deleteById(id);
	}
}
