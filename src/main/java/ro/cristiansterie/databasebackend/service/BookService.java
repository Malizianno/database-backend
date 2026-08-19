package ro.cristiansterie.databasebackend.service;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.cristiansterie.databasebackend.dto.BookDTO;
import ro.cristiansterie.databasebackend.repository.BookRepository;
import ro.cristiansterie.databasebackend.util.converter.models.BookModelConverter;

import java.util.Set;
import java.util.UUID;

@Service
public class BookService {
	private final BookRepository repo;
	private final BookModelConverter converter;

	public BookService(BookRepository repo, BookModelConverter converter) {
		this.repo = repo;
		this.converter = converter;
	}

	@Transactional(readOnly = true)
	public BookDTO findById(UUID id) {
		return converter.toDto(repo.findById(id)
		                           .orElse(null));
	}

	@Transactional(readOnly = true)
	public Set<BookDTO> findAll() {
		return converter.toDtoSet(repo.findAll());
	}

	@Transactional
	public BookDTO save(BookDTO dto) {
		return converter.toDto(repo.save(converter.toEntity(dto)));
	}

	@Transactional
	public BookDTO update(UUID id, BookDTO dto) {
		if (id == null || dto.id() == null || id.equals(dto.id())) {
			throw new IllegalArgumentException("Invalid ID: " + id);
		}

		var entity = repo.findById(id)
		                 .orElseThrow(() -> new EntityNotFoundException("Could not find book with ID: " + id));

		return converter.toDto(repo.save(converter.toEntity(dto)));
	}

	@Transactional
	public void delete(UUID id) {
		repo.deleteById(id);
	}
}
