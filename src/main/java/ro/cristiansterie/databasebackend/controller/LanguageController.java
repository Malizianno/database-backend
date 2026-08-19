package ro.cristiansterie.databasebackend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.cristiansterie.databasebackend.dto.LanguageDTO;
import ro.cristiansterie.databasebackend.service.LanguageService;

import java.util.Set;
import java.util.UUID;

@RestController
@RequestMapping("/languages")
public class LanguageController {
	private final LanguageService service;

	public LanguageController(LanguageService service) {
		this.service = service;
	}

	@GetMapping("/{id}")
	public ResponseEntity<LanguageDTO> findById(@PathVariable UUID id) {
		return ResponseEntity.ok(service.findById(id));
	}

	@GetMapping("/")
	public ResponseEntity<Set<LanguageDTO>> findAll() {
		return ResponseEntity.ok(service.findAll());
	}

	@PostMapping("/")
	public ResponseEntity<LanguageDTO> save(@RequestBody LanguageDTO dto) {
		return ResponseEntity.ok(service.save(dto));
	}

	@PutMapping("/{id}")
	public ResponseEntity<LanguageDTO> update(@PathVariable UUID id, @RequestBody LanguageDTO dto) {
		return ResponseEntity.ok(service.update(id, dto));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable UUID id) {
		service.delete(id);
		return ResponseEntity.noContent()
		                     .build();
	}
}
