package ro.cristiansterie.databasebackend.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import ro.cristiansterie.databasebackend.util.enums.CollectionType;

import java.util.UUID;

public record CollectionDTO(
		@JsonProperty(access = JsonProperty.Access.READ_ONLY)
		UUID id,
		UUID userId,
		String name,
		String description,
		CollectionType type) {
}
