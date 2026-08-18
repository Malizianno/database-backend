package ro.cristiansterie.databasebackend.service;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import ro.cristiansterie.databasebackend.dto.DomainDTO;
import ro.cristiansterie.databasebackend.repository.DomainRepository;
import ro.cristiansterie.databasebackend.util.converter.models.DomainModelConverter;

import java.util.Set;

@Service
public class DomainService {
	private final DomainRepository repo;
	private final DomainModelConverter converter;

	public DomainService(DomainRepository repo, DomainModelConverter converter) {
		this.repo = repo;
		this.converter = converter;
	}

	public DomainDTO findById(Long id) {
		return converter.toDto(repo.findById(id)
		                           .orElse(null));
	}

	public Set<DomainDTO> findAll() {
		return converter.toDtoSet(repo.findAll());
	}

	public DomainDTO save(DomainDTO dto) {
		return converter.toDto(repo.save(converter.toEntity(dto)));
	}

	public DomainDTO update(Long id, DomainDTO dto) {
		if (id == null || dto.id() == null || id.equals(dto.id())) {
			throw new IllegalArgumentException("Invalid ID: " + id);
		}

		var entity = repo.findById(id)
		                 .orElseThrow(() -> new EntityNotFoundException("Could not find domain with ID: " + id));

		return converter.toDto(repo.save(converter.toEntity(dto)));
	}

	public void delete(Long id) {
		repo.deleteById(id);
	}
}
