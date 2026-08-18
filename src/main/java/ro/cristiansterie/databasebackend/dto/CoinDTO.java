package ro.cristiansterie.databasebackend.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import ro.cristiansterie.databasebackend.util.enums.CollectionItemConditionType;

public record CoinDTO(
		@JsonProperty(access = JsonProperty.Access.READ_ONLY)
		Long id,
		Long collectionId,
		Long materialId,
		Long denominationId,
		CollectionItemConditionType condition,
		Integer year,
		Double diameter,
		String description,
		String link,
		Integer value,
		Integer extraYear,
		Integer units) {
}
