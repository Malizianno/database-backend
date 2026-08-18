package ro.cristiansterie.databasebackend.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record BookDTO(
		@JsonProperty(access = JsonProperty.Access.READ_ONLY)
		Long id,
		Long collectionId,
		String title,
		String author,
		String description,
		String isbn,
		Integer pages,
		String link,
		Integer publishedYear,
		Integer printedYear,
		Long domainId,
		Long languageId) {
}
