package ro.cristiansterie.database_backend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ro.cristiansterie.database_backend.dto.UserDTO;
import ro.cristiansterie.database_backend.service.UserService;

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
}
