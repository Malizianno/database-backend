package ro.cristiansterie.databasebackend.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public record LanguageDTO(
		@JsonProperty(access = JsonProperty.Access.READ_ONLY)
		UUID id,
		UUID countryId,
		String name) {
}
