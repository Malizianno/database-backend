package ro.cristiansterie.databasebackend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ro.cristiansterie.databasebackend.dto.BiometricLoginRequestDTO;
import ro.cristiansterie.databasebackend.dto.BiometricRegisterRequestDTO;
import ro.cristiansterie.databasebackend.dto.LoginResponseDTO;
import ro.cristiansterie.databasebackend.dto.UserPassLoginRequestDTO;
import ro.cristiansterie.databasebackend.service.AuthService;

@RestController
@RequestMapping("/auth")
public class AuthController {

	private final AuthService service;

	public AuthController(AuthService service) {
		this.service = service;
	}

	@PostMapping("/login")
	public ResponseEntity<LoginResponseDTO> login(@Validated @RequestBody UserPassLoginRequestDTO loginRequest) {
		return ResponseEntity.ok(service.authenticateUserPass(loginRequest));
	}

	@PostMapping("/bio")
	public ResponseEntity<LoginResponseDTO> bio(@Validated @RequestBody BiometricLoginRequestDTO loginRequest) {
		return ResponseEntity.ok(service.authenticateBiometrics(loginRequest));
	}

	@PostMapping("/bio/register")
	public ResponseEntity<Boolean> register(@Validated @RequestBody BiometricRegisterRequestDTO registerRequest) {
		return ResponseEntity.ok(service.registerBiometricAuthentication(registerRequest));
	}

	@GetMapping("/bio/challenge")
	public ResponseEntity<String> challenger(@Validated @RequestParam String username) {
		return ResponseEntity.ok(service.getChallengeBiometricsAuthentication(username));
	}
}
