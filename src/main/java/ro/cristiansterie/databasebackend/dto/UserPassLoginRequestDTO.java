package ro.cristiansterie.databasebackend.dto;

import org.jspecify.annotations.NonNull;

public record UserPassLoginRequestDTO(
		@NonNull
		String username,
		@NonNull
		String password) {
}
