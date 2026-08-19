package ro.cristiansterie.databasebackend.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public record CountryDTO(
		@JsonProperty(access = JsonProperty.Access.READ_ONLY)
		UUID id,
		String name,
		String continent,
		String flag) {
}
