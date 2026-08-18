package ro.cristiansterie.databasebackend.service;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import ro.cristiansterie.databasebackend.dto.DenominationDTO;
import ro.cristiansterie.databasebackend.repository.DenominationRepository;
import ro.cristiansterie.databasebackend.util.converter.models.DenominationModelConverter;

import java.util.Set;

@Service
public class DenominationService {
	private final DenominationRepository repo;
	private final DenominationModelConverter converter;

	public DenominationService(DenominationRepository repo, DenominationModelConverter converter) {
		this.repo = repo;
		this.converter = converter;
	}

	public DenominationDTO findById(Long id) {
		return converter.toDto(repo.findById(id)
		                           .orElse(null));
	}

	public Set<DenominationDTO> findAll() {
		return converter.toDtoSet(repo.findAll());
	}

	public DenominationDTO save(DenominationDTO dto) {
		return converter.toDto(repo.save(converter.toEntity(dto)));
	}

	public DenominationDTO update(Long id, DenominationDTO dto) {
		if (id == null || dto.id() == null || id.equals(dto.id())) {
			throw new IllegalArgumentException("Invalid ID: " + id);
		}

		var entity = repo.findById(id)
		                 .orElseThrow(() -> new EntityNotFoundException("Could not find denomination with ID: " + id));

		return converter.toDto(repo.save(converter.toEntity(dto)));
	}

	public void delete(Long id) {
		repo.deleteById(id);
	}
}
