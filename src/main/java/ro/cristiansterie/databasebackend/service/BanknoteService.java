package ro.cristiansterie.databasebackend.service;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.cristiansterie.databasebackend.dto.BanknoteDTO;
import ro.cristiansterie.databasebackend.repository.BanknoteRepository;
import ro.cristiansterie.databasebackend.util.converter.models.BanknoteModelConverter;

import java.util.Set;
import java.util.UUID;

@Service
public class BanknoteService {
	private final BanknoteRepository repo;
	private final BanknoteModelConverter converter;

	public BanknoteService(BanknoteRepository repo, BanknoteModelConverter converter) {
		this.repo = repo;
		this.converter = converter;
	}

	@Transactional(readOnly = true)
	public BanknoteDTO findById(UUID id) {
		return converter.toDto(repo.findById(id)
		                           .orElse(null));
	}

	@Transactional(readOnly = true)
	public Set<BanknoteDTO> findAll() {
		return converter.toDtoSet(repo.findAll());
	}

	@Transactional
	public BanknoteDTO save(BanknoteDTO dto) {
		return converter.toDto(repo.save(converter.toEntity(dto)));
	}

	@Transactional
	public BanknoteDTO update(UUID id, BanknoteDTO dto) {
		if (id == null || dto.id() == null || id.equals(dto.id())) {
			throw new IllegalArgumentException("Invalid ID: " + id);
		}

		var entity = repo.findById(id)
		                 .orElseThrow(() -> new EntityNotFoundException("Could not find banknote with ID: " + id));

		return converter.toDto(repo.save(converter.toEntity(dto)));
	}

	@Transactional
	public void delete(UUID id) {
		repo.deleteById(id);
	}
}
