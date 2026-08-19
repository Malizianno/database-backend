package ro.cristiansterie.databasebackend.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import ro.cristiansterie.databasebackend.util.enums.CollectionItemConditionType;

import java.util.UUID;

public record BanknoteDTO(
		@JsonProperty(access = JsonProperty.Access.READ_ONLY)
		UUID id,
		UUID collectionId,
		UUID materialId,
		UUID denominationId,
		CollectionItemConditionType condition,
		Integer year,
		Double length,
		Double thickness,
		Double width,
		String description,
		String link,
		Integer numericValue,
		Integer extraYear,
		Integer units) {
}
