package ro.cristiansterie.databasebackend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.cristiansterie.databasebackend.dto.CollectionDTO;
import ro.cristiansterie.databasebackend.service.CollectionService;

import java.util.Set;

@RestController
@RequestMapping("/collections")
public class CollectionController {
	private final CollectionService service;

	public CollectionController(CollectionService service) {
		this.service = service;
	}

	@GetMapping("/{id}")
	public ResponseEntity<CollectionDTO> findById(@PathVariable Long id) {
		return ResponseEntity.ok(service.findById(id));
	}

	@GetMapping("/")
	public ResponseEntity<Set<CollectionDTO>> findAll() {
		return ResponseEntity.ok(service.findAll());
	}

	@PostMapping("/")
	public ResponseEntity<CollectionDTO> save(@RequestBody CollectionDTO dto) {
		return ResponseEntity.ok(service.save(dto));
	}

	@PutMapping("/{id}")
	public ResponseEntity<CollectionDTO> update(@PathVariable Long id, @RequestBody CollectionDTO dto) {
		return ResponseEntity.ok(service.update(id, dto));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		service.delete(id);
		return ResponseEntity.noContent()
		                     .build();
	}
}
