package ro.cristiansterie.databasebackend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.cristiansterie.databasebackend.dto.CoinDTO;
import ro.cristiansterie.databasebackend.service.CoinService;

import java.util.Set;

@RestController
@RequestMapping("/coins")
public class CoinController {
	private final CoinService service;

	public CoinController(CoinService service) {
		this.service = service;
	}

	@GetMapping("/{id}")
	public ResponseEntity<CoinDTO> findById(@PathVariable Long id) {
		return ResponseEntity.ok(service.findById(id));
	}

	@GetMapping("/")
	public ResponseEntity<Set<CoinDTO>> findAll() {
		return ResponseEntity.ok(service.findAll());
	}

	@PostMapping("/")
	public ResponseEntity<CoinDTO> save(@RequestBody CoinDTO dto) {
		return ResponseEntity.ok(service.save(dto));
	}

	@PutMapping("/{id}")
	public ResponseEntity<CoinDTO> update(@PathVariable Long id, @RequestBody CoinDTO dto) {
		return ResponseEntity.ok(service.update(id, dto));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		service.delete(id);
		return ResponseEntity.noContent()
		                     .build();
	}
}
