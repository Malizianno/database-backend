package ro.cristiansterie.databasebackend.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import ro.cristiansterie.databasebackend.util.enums.CollectionItemConditionType;

public record BanknoteDTO(
		@JsonProperty(access = JsonProperty.Access.READ_ONLY)
		Long id,
		Long collectionId,
		Long materialId,
		Long denominationId,
		CollectionItemConditionType condition,
		Integer year,
		Double length,
		Double thickness,
		Double width,
		String description,
		String link,
		Integer value,
		Integer extraYear,
		Integer units) {
}
