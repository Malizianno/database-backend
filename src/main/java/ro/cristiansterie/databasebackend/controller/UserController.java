package ro.cristiansterie.databasebackend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import ro.cristiansterie.databasebackend.dto.UserDTO;
import ro.cristiansterie.databasebackend.service.UserService;

import java.util.List;

@RestController("/users")
public class UserController {

    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @GetMapping("/")
    public ResponseEntity<List<UserDTO>> findAll() {
        List<UserDTO> users = service.getAll();

        return ResponseEntity.ok(users);
    }

	@GetMapping("/{id}")
	public ResponseEntity<UserDTO> findById(@PathVariable Long id) {
		UserDTO user = service.getById(id);

		return ResponseEntity.ok(user);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteById(@PathVariable Long id) {

	}
}
