package ro.cristiansterie.databasebackend.dto;

public record UserPassLoginRequestDTO(
		String username,
		String password) {
}
