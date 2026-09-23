package ro.cristiansterie.databasebackend.dto;

import org.jspecify.annotations.NonNull;

public record BiometricLoginRequestDTO(
		@NonNull
		String username,
		@NonNull
		String originalChallenge,
		@NonNull
		String signature) {
}
