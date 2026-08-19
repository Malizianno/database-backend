package ro.cristiansterie.databasebackend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.cristiansterie.databasebackend.dto.BookDTO;
import ro.cristiansterie.databasebackend.service.BookService;

import java.util.Set;

@RestController
@RequestMapping("/books")
public class BookController {
	private final BookService service;

	public BookController(BookService service) {
		this.service = service;
	}

	@GetMapping("/{id}")
	public ResponseEntity<BookDTO> findById(@PathVariable Long id) {
		return ResponseEntity.ok(service.findById(id));
	}

	@GetMapping("/")
	public ResponseEntity<Set<BookDTO>> findAll() {
		return ResponseEntity.ok(service.findAll());
	}

	@PostMapping("/")
	public ResponseEntity<BookDTO> save(@RequestBody BookDTO dto) {
		return ResponseEntity.ok(service.save(dto));
	}

	@PutMapping("/{id}")
	public ResponseEntity<BookDTO> update(@PathVariable Long id, @RequestBody BookDTO dto) {
		return ResponseEntity.ok(service.update(id, dto));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		service.delete(id);
		return ResponseEntity.noContent()
		                     .build();
	}
}
