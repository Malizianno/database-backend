package ro.cristiansterie.databasebackend.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import ro.cristiansterie.databasebackend.util.enums.CollectionType;

public record CollectionDTO(
		@JsonProperty(access = JsonProperty.Access.READ_ONLY)
		Long id,
		String name,
		String description,
		Long userId,
		CollectionType type) {
}
