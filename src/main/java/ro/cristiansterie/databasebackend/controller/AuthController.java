package ro.cristiansterie.databasebackend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ro.cristiansterie.databasebackend.dto.LoginRequestDTO;
import ro.cristiansterie.databasebackend.dto.LoginResponseDTO;
import ro.cristiansterie.databasebackend.service.AuthService;

@RestController
@RequestMapping("/auth")
public class AuthController {

	private final AuthService service;

	public AuthController(AuthService service) {
		this.service = service;
	}

	@PostMapping("/login")
	public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO loginRequest) {
		return ResponseEntity.ok(service.authenticate(loginRequest));
	}
}
