package ro.cristiansterie.databasebackend.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public record BookDTO(
		@JsonProperty(access = JsonProperty.Access.READ_ONLY)
		UUID id,
		UUID collectionId,
		UUID domainId,
		UUID languageId,
		String title,
		String author,
		String description,
		String isbn,
		Integer pages,
		String link,
		Integer publishedYear,
		Integer printedYear) {
}
