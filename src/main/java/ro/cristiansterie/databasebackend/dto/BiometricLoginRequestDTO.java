package ro.cristiansterie.databasebackend.dto;

public record BiometricLoginRequestDTO(
		String username,
		String originalChallenge,
		String signature) {
}
