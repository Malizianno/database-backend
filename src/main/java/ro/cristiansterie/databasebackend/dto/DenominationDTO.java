package ro.cristiansterie.databasebackend.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record DenominationDTO(
		@JsonProperty(access = JsonProperty.Access.READ_ONLY)
		Long id,
		String title,
		Long countryId) {
}
