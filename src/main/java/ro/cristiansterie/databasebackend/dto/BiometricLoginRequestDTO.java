package ro.cristiansterie.databasebackend.dto;


import lombok.NonNull;

public record BiometricLoginRequestDTO(
		@NonNull
		String username,
		@NonNull
		String originalChallenge,
		@NonNull
		String signature) {
}
