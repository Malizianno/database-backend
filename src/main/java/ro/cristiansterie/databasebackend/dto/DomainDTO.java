package ro.cristiansterie.databasebackend.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record DomainDTO(
		@JsonProperty(access = JsonProperty.Access.READ_ONLY)
		Long id,
		String name,
		String description) {
}
