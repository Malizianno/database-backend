package ro.cristiansterie.databasebackend.service;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.cristiansterie.databasebackend.dto.CoinDTO;
import ro.cristiansterie.databasebackend.repository.CoinRepository;
import ro.cristiansterie.databasebackend.util.Validator;
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
		if (dto == null || !Validator.isUUIDValid(id)) {
			throw new IllegalArgumentException("Invalid ID: " + id);
		}

		var entity = repo.findById(id)
		                 .orElseThrow(() -> new EntityNotFoundException("Could not find coin with ID: " + id));

		entity.setCollectionId(dto.collectionId());
		entity.setCondition(dto.condition());
		entity.setLink(dto.link());
		entity.setDiameter(dto.diameter());
		entity.setDescription(dto.description());
		entity.setYear(dto.year());
		entity.setUnits(dto.units());
		entity.setDenominationId(dto.denominationId());
		entity.setExtraYear(dto.extraYear());
		entity.setMaterialId(dto.materialId());
		entity.setNumericValue(dto.numericValue());

		return converter.toDto(repo.save(entity));
	}

	@Transactional
	public void delete(UUID id) {
		repo.deleteById(id);
	}
}
