package ro.cristiansterie.databasebackend.dto;


import lombok.NonNull;

public record BiometricRegisterRequestDTO(
		@NonNull
		String username,
		@NonNull
		String key) {
}
