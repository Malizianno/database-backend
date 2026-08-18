package ro.cristiansterie.databasebackend.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import ro.cristiansterie.databasebackend.util.enums.CollectionType;

public record ImageDTO(
		@JsonProperty(access = JsonProperty.Access.READ_ONLY)
		Long id,
		CollectionType itemType,
		Long itemId,
		String imageUrl) {
}
