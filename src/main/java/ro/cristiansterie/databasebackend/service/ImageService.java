package ro.cristiansterie.databasebackend.service;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.cristiansterie.databasebackend.dto.ImageDTO;
import ro.cristiansterie.databasebackend.repository.ImageRepository;
import ro.cristiansterie.databasebackend.util.converter.models.ImageModelConverter;

import java.util.Set;

@Service
public class ImageService {
	private final ImageRepository repo;
	private final ImageModelConverter converter;

	public ImageService(ImageRepository repo, ImageModelConverter converter) {
		this.repo = repo;
		this.converter = converter;
	}

	@Transactional(readOnly = true)
	public ImageDTO findById(Long id) {
		return converter.toDto(repo.findById(id)
		                           .orElse(null));
	}

	@Transactional(readOnly = true)
	public Set<ImageDTO> findAll() {
		return converter.toDtoSet(repo.findAll());
	}

	@Transactional
	public ImageDTO save(ImageDTO dto) {
		return converter.toDto(repo.save(converter.toEntity(dto)));
	}

	@Transactional
	public ImageDTO update(Long id, ImageDTO dto) {
		if (id == null || dto.id() == null || id.equals(dto.id())) {
			throw new IllegalArgumentException("Invalid ID: " + id);
		}

		var entity = repo.findById(id)
		                 .orElseThrow(() -> new EntityNotFoundException("Could not find image with ID: " + id));

		return converter.toDto(repo.save(converter.toEntity(dto)));
	}

	@Transactional
	public void delete(Long id) {
		repo.deleteById(id);
	}
}
