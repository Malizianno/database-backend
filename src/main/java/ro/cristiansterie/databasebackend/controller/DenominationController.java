package ro.cristiansterie.databasebackend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.cristiansterie.databasebackend.dto.DenominationDTO;
import ro.cristiansterie.databasebackend.service.DenominationService;

import java.util.Set;
import java.util.UUID;

@RestController
@RequestMapping("/denominations")
public class DenominationController {
	private final DenominationService service;

	public DenominationController(DenominationService service) {
		this.service = service;
	}

	@GetMapping("/{id}")
	public ResponseEntity<DenominationDTO> findById(@PathVariable UUID id) {
		return ResponseEntity.ok(service.findById(id));
	}

	@GetMapping("/")
	public ResponseEntity<Set<DenominationDTO>> findAll() {
		return ResponseEntity.ok(service.findAll());
	}

	@PostMapping("/")
	public ResponseEntity<DenominationDTO> save(@RequestBody DenominationDTO dto) {
		return ResponseEntity.ok(service.save(dto));
	}

	@PutMapping("/{id}")
	public ResponseEntity<DenominationDTO> update(@PathVariable UUID id, @RequestBody DenominationDTO dto) {
		return ResponseEntity.ok(service.update(id, dto));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable UUID id) {
		service.delete(id);
		return ResponseEntity.noContent()
		                     .build();
	}
}
