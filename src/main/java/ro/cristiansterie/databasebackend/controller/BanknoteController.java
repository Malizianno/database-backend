package ro.cristiansterie.databasebackend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.cristiansterie.databasebackend.dto.BanknoteDTO;
import ro.cristiansterie.databasebackend.service.BanknoteService;

import java.util.Set;

@RestController
@RequestMapping("/banknotes")
public class BanknoteController {
	private final BanknoteService service;

	public BanknoteController(BanknoteService service) {
		this.service = service;
	}

	@GetMapping("/{id}")
	public ResponseEntity<BanknoteDTO> findById(@PathVariable Long id) {
		return ResponseEntity.ok(service.findById(id));
	}

	@GetMapping("/")
	public ResponseEntity<Set<BanknoteDTO>> findAll() {
		return ResponseEntity.ok(service.findAll());
	}

	@PostMapping("/")
	public ResponseEntity<BanknoteDTO> save(@RequestBody BanknoteDTO dto) {
		return ResponseEntity.ok(service.save(dto));
	}

	@PutMapping("/{id}")
	public ResponseEntity<BanknoteDTO> update(@PathVariable Long id, @RequestBody BanknoteDTO dto) {
		return ResponseEntity.ok(service.update(id, dto));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		service.delete(id);
		return ResponseEntity.noContent()
		                     .build();
	}
}
