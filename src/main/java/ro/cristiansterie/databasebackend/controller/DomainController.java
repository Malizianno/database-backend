package ro.cristiansterie.databasebackend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.cristiansterie.databasebackend.dto.DomainDTO;
import ro.cristiansterie.databasebackend.service.DomainService;
import ro.cristiansterie.databasebackend.service.ImageService;

import java.util.Set;

@RestController
@RequestMapping("/domains")
public class DomainController {
	private final DomainService service;

	public DomainController(DomainService service) {
		this.service = service;
	}

	@GetMapping("/{id}")
	public ResponseEntity<DomainDTO> findById(@PathVariable Long id) {
		return ResponseEntity.ok(service.findById(id));
	}

	@GetMapping("/")
	public ResponseEntity<Set<DomainDTO>> findAll() {
		return ResponseEntity.ok(service.findAll());
	}

	@PostMapping("/")
	public ResponseEntity<DomainDTO> save(@RequestBody DomainDTO dto) {
		return ResponseEntity.ok(service.save(dto));
	}

	@PutMapping("/{id}")
	public ResponseEntity<DomainDTO> update(@PathVariable Long id, @RequestBody DomainDTO dto) {
		return ResponseEntity.ok(service.update(id, dto));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		service.delete(id);
		return ResponseEntity.noContent()
		                     .build();
	}
}
