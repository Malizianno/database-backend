package ro.cristiansterie.databasebackend.service;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.cristiansterie.databasebackend.dto.BanknoteDTO;
import ro.cristiansterie.databasebackend.dto.BookDTO;
import ro.cristiansterie.databasebackend.repository.BanknoteRepository;
import ro.cristiansterie.databasebackend.repository.BookRepository;
import ro.cristiansterie.databasebackend.util.converter.models.BanknoteModelConverter;
import ro.cristiansterie.databasebackend.util.converter.models.BookModelConverter;

import java.util.List;
import java.util.Set;

@Service
public class BanknoteService {
	private final BanknoteRepository repo;
	private final BanknoteModelConverter converter;

	public BanknoteService(BanknoteRepository repo, BanknoteModelConverter converter) {
		this.repo = repo;
		this.converter = converter;
	}

	@Transactional(readOnly = true)
	public BanknoteDTO findById(Long id) {
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
	public BanknoteDTO update(Long id, BanknoteDTO dto) {
		if (id == null || dto.id() == null || id.equals(dto.id())) {
			throw new IllegalArgumentException("Invalid ID: " + id);
		}

		var entity = repo.findById(id)
		                 .orElseThrow(() -> new EntityNotFoundException("Could not find banknote with ID: " + id));

		return converter.toDto(repo.save(converter.toEntity(dto)));
	}

	@Transactional
	public void delete(Long id) {
		repo.deleteById(id);
	}
}
