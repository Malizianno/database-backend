package ro.cristiansterie.databasebackend.dto;


import lombok.NonNull;

public record UserPassLoginRequestDTO(
		@NonNull
		String username,
		@NonNull
		String password) {
}
