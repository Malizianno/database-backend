package ro.cristiansterie.databasebackend.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ro.cristiansterie.databasebackend.dto.BiometricLoginRequestDTO;
import ro.cristiansterie.databasebackend.dto.BiometricRegisterRequestDTO;
import ro.cristiansterie.databasebackend.dto.LoginResponseDTO;
import ro.cristiansterie.databasebackend.dto.UserPassLoginRequestDTO;
import ro.cristiansterie.databasebackend.service.AuthService;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

	private final AuthService service;

	@PostMapping("/login")
	@PreAuthorize("#username == authentication.principal.user.username")
	public ResponseEntity<LoginResponseDTO> login(@Validated @RequestBody UserPassLoginRequestDTO loginRequest, @RequestParam String username) {
		if (username == null)
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
			                     .build();

		return ResponseEntity.ok(service.authenticateUserPass(loginRequest));
	}

	@PostMapping("/bio")
	@PreAuthorize("#username == authentication.principal.user.username")
	public ResponseEntity<LoginResponseDTO> bio(@Validated @RequestBody BiometricLoginRequestDTO loginRequest, @RequestParam String username) {
		if (username == null)
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
			                     .build();

		return ResponseEntity.ok(service.authenticateBiometrics(loginRequest));
	}

	@PostMapping("/bio/register")
	@PreAuthorize("#username == authentication.principal.user.username")
	public ResponseEntity<Boolean> register(@Validated @RequestBody BiometricRegisterRequestDTO registerRequest, @RequestParam String username) {
		if (username == null)
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
			                     .build();

		return ResponseEntity.ok(service.registerBiometricAuthentication(registerRequest));
	}

	@GetMapping("/bio/challenge")
	@PreAuthorize("#username == authentication.principal.user.username")
	public ResponseEntity<String> challenger(@Validated @RequestParam String username) {
		if (username == null)
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
			                     .build();

		return ResponseEntity.ok(service.getChallengeBiometricsAuthentication(username));
	}
}
