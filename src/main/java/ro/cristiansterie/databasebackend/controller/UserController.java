package ro.cristiansterie.databasebackend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ro.cristiansterie.databasebackend.dto.UserDTO;
import ro.cristiansterie.databasebackend.service.UserService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/users")
public class UserController {

	private final UserService service;

	public UserController(UserService service) {
		this.service = service;
	}

	@GetMapping("/")
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<List<UserDTO>> findAll() {
		return ResponseEntity.ok(service.findAll());
	}

	@GetMapping("/{id}")
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<UserDTO> findById(@PathVariable UUID id) {
		return ResponseEntity.ok(service.findById(id));
	}

	@GetMapping("/profile")
	@PreAuthorize("#username == authentication.principal.user.username")
	public ResponseEntity<UserDTO> findByUsername(@RequestParam String username) {
		return ResponseEntity.ok(service.findByUsername(username));
	}

	@PostMapping("/")
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<UserDTO> save(@RequestBody UserDTO user) {
		return ResponseEntity.ok(service.save(user));
	}

	@PutMapping("/{id}")
	@PreAuthorize("#id == authentication.principal.user.id or hasAuthority('ADMIN')")
	public ResponseEntity<UserDTO> update(@PathVariable UUID id, @RequestBody UserDTO user) {
		return ResponseEntity.ok(service.update(id, user));
	}

	@DeleteMapping("/{id}")
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<?> delete(@PathVariable UUID id) {
		return ResponseEntity.ok(service.delete(id));
	}
}
