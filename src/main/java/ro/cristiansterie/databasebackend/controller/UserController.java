package ro.cristiansterie.databasebackend.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ro.cristiansterie.databasebackend.dto.UserDTO;
import ro.cristiansterie.databasebackend.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/users")
@Slf4j
public class UserController {

	private final UserService service;

	public UserController(UserService service) {
		this.service = service;
	}

	@GetMapping("/")
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<List<UserDTO>> getAll() {
		log.info("Find all users");
		return ResponseEntity.ok(service.getAll());
	}

	@GetMapping("/{id}")
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<UserDTO> getById(@PathVariable Long id) {
		log.info("Find user by id: {}", id);
		return ResponseEntity.ok(service.getById(id));
	}

	@GetMapping("/profile")
	@PreAuthorize("#username == authentication.principal.user.username")
	public ResponseEntity<UserDTO> getByUsername(@RequestParam String username) {
		log.info("Find user by username: {}", username);
		return ResponseEntity.ok(service.getByUsername(username));
	}

	@PostMapping("/{id}")
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<UserDTO> addUser(@PathVariable Long id, @RequestBody UserDTO user) {
		log.info("Add user by id: {}", id);
		return ResponseEntity.ok(service.addUser(user));
	}

	@PutMapping("/{id}")
	@PreAuthorize("#id == authentication.principal.user.id or hasAuthority('ADMIN')")
	public ResponseEntity<UserDTO> updateUser(@PathVariable Long id, @RequestBody UserDTO user) {
		log.info("Update user by id: {}", id);
		return ResponseEntity.ok(service.updateUser(user));
	}

	@DeleteMapping("/{id}")
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<?> deleteById(@PathVariable Long id) {
		log.info("Delete user by id: {}", id);
		return ResponseEntity.ok(service.deleteById(id));
	}
}
