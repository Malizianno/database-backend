package ro.cristiansterie.databasebackend.service;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import ro.cristiansterie.databasebackend.dto.CoinDTO;
import ro.cristiansterie.databasebackend.repository.CoinRepository;
import ro.cristiansterie.databasebackend.util.converter.models.CoinModelConverter;

import java.util.Set;

@Service
public class CoinService {
	private final CoinRepository repo;
	private final CoinModelConverter converter;

	public CoinService(CoinRepository repo, CoinModelConverter converter) {
		this.repo = repo;
		this.converter = converter;
	}

	public CoinDTO findById(Long id) {
		return converter.toDto(repo.findById(id)
		                           .orElse(null));
	}

	public Set<CoinDTO> findAll() {
		return converter.toDtoSet(repo.findAll());
	}

	public CoinDTO save(CoinDTO dto) {
		return converter.toDto(repo.save(converter.toEntity(dto)));
	}

	public CoinDTO update(Long id, CoinDTO dto) {
		if (id == null || dto.id() == null || id.equals(dto.id())) {
			throw new IllegalArgumentException("Invalid ID: " + id);
		}

		var entity = repo.findById(id)
		                 .orElseThrow(() -> new EntityNotFoundException("Could not find coin with ID: " + id));

		return converter.toDto(repo.save(converter.toEntity(dto)));
	}

	public void delete(Long id) {
		repo.deleteById(id);
	}
}
