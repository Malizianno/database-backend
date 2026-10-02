package ro.cristiansterie.databasebackend.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ro.cristiansterie.databasebackend.dto.BookDTO;
import ro.cristiansterie.databasebackend.service.BookService;

import java.util.Set;
import java.util.UUID;

@RestController
@RequestMapping("/books")
@RequiredArgsConstructor
public class BookController {
	private final BookService service;

	@GetMapping("/{id}")
	@PreAuthorize("#username == authentication.principal.user.username")
	public ResponseEntity<BookDTO> findById(@PathVariable UUID id, @RequestParam String username) {
		if (username == null)
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
			                     .build();

		return ResponseEntity.ok(service.findById(id));
	}

	@GetMapping("/")
	@PreAuthorize("#username == authentication.principal.user.username")
	public ResponseEntity<Set<BookDTO>> findAll(@RequestParam String username) {
		if (username == null)
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
			                     .build();

		return ResponseEntity.ok(service.findAll());
	}

	@PostMapping("/")
	@PreAuthorize("#username == authentication.principal.user.username")
	public ResponseEntity<BookDTO> save(@RequestBody BookDTO dto, @RequestParam String username) {
		if (username == null)
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
			                     .build();

		return ResponseEntity.ok(service.save(dto));
	}

	@PutMapping("/{id}")
	@PreAuthorize("#username == authentication.principal.user.username")
	public ResponseEntity<BookDTO> update(@PathVariable UUID id, @RequestBody BookDTO dto, @RequestParam String username) {
		if (username == null)
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
			                     .build();

		return ResponseEntity.ok(service.update(id, dto));
	}

	@DeleteMapping("/{id}")
	@PreAuthorize("#username == authentication.principal.user.username")
	public ResponseEntity<Void> delete(@PathVariable UUID id, @RequestParam String username) {
		if (username == null)
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
			                     .build();

		service.delete(id);
		return ResponseEntity.noContent()
		                     .build();
	}
}
