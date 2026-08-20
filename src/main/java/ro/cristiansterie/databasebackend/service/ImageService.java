package ro.cristiansterie.databasebackend.service;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.cristiansterie.databasebackend.dto.ImageDTO;
import ro.cristiansterie.databasebackend.repository.ImageRepository;
import ro.cristiansterie.databasebackend.util.Validator;
import ro.cristiansterie.databasebackend.util.converter.models.ImageModelConverter;

import java.util.Set;
import java.util.UUID;

@Service
public class ImageService {
	private final ImageRepository repo;
	private final ImageModelConverter converter;

	public ImageService(ImageRepository repo, ImageModelConverter converter) {
		this.repo = repo;
		this.converter = converter;
	}

	@Transactional(readOnly = true)
	public ImageDTO findById(UUID id) {
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
	public ImageDTO update(UUID id, ImageDTO dto) {
		if (dto == null || !Validator.isUUIDValid(id)) {
			throw new IllegalArgumentException("Invalid ID: " + id);
		}

		var entity = repo.findById(id)
		                 .orElseThrow(() -> new EntityNotFoundException("Could not find image with ID: " + id));

		entity.setImageUrl(dto.imageUrl());
		entity.setItemId(dto.itemId());
		entity.setItemType(dto.itemType());

		return converter.toDto(repo.save(entity));
	}

	@Transactional
	public void delete(UUID id) {
		repo.deleteById(id);
	}
}
