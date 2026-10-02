package ro.cristiansterie.databasebackend.dto;

import lombok.NonNull;

public record ErrorResponseDTO(
		@NonNull String message,
		@NonNull String error,
		int status) {
}
