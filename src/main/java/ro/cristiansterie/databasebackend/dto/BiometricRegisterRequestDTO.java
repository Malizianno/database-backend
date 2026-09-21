package ro.cristiansterie.databasebackend.dto;

import org.jspecify.annotations.NonNull;

public record BiometricRegisterRequestDTO(
		@NonNull
		String username,
		@NonNull
		String key) {
}
