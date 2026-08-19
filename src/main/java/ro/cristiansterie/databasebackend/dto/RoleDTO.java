package ro.cristiansterie.databasebackend.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public record RoleDTO(
		@JsonProperty(access = JsonProperty.Access.READ_ONLY)
		UUID id,
		String name,
		String description) {
}
