package ro.cristiansterie.databasebackend.service;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.cristiansterie.databasebackend.dto.CoinDTO;
import ro.cristiansterie.databasebackend.repository.CoinRepository;
import ro.cristiansterie.databasebackend.util.converter.models.CoinModelConverter;

import java.util.Set;
import java.util.UUID;

@Service
public class CoinService {
	private final CoinRepository repo;
	private final CoinModelConverter converter;

	public CoinService(CoinRepository repo, CoinModelConverter converter) {
		this.repo = repo;
		this.converter = converter;
	}

	@Transactional(readOnly = true)
	public CoinDTO findById(UUID id) {
		return converter.toDto(repo.findById(id)
		                           .orElse(null));
	}

	@Transactional(readOnly = true)
	public Set<CoinDTO> findAll() {
		return converter.toDtoSet(repo.findAll());
	}

	@Transactional
	public CoinDTO save(CoinDTO dto) {
		return converter.toDto(repo.save(converter.toEntity(dto)));
	}

	@Transactional
	public CoinDTO update(UUID id, CoinDTO dto) {
		if (id == null || dto.id() == null || id.equals(dto.id())) {
			throw new IllegalArgumentException("Invalid ID: " + id);
		}

		var entity = repo.findById(id)
		                 .orElseThrow(() -> new EntityNotFoundException("Could not find coin with ID: " + id));

		return converter.toDto(repo.save(converter.toEntity(dto)));
	}

	@Transactional
	public void delete(UUID id) {
		repo.deleteById(id);
	}
}
