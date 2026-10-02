package ro.cristiansterie.databasebackend.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ro.cristiansterie.databasebackend.dto.CoinDTO;
import ro.cristiansterie.databasebackend.service.CoinService;

import java.util.Set;
import java.util.UUID;

@RestController
@RequestMapping("/coins")
@RequiredArgsConstructor
public class CoinController {
	private final CoinService service;

	@GetMapping("/{id}")
	@PreAuthorize("#username == authentication.principal.user.username")
	public ResponseEntity<CoinDTO> findById(@PathVariable UUID id, @RequestParam String username) {
		if (username == null)
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
			                     .build();

		return ResponseEntity.ok(service.findById(id));
	}

	@GetMapping("/")
	@PreAuthorize("#username == authentication.principal.user.username")
	public ResponseEntity<Set<CoinDTO>> findAll(@RequestParam String username) {
		if (username == null)
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
			                     .build();

		return ResponseEntity.ok(service.findAll());
	}

	@PostMapping("/")
	@PreAuthorize("#username == authentication.principal.user.username")
	public ResponseEntity<CoinDTO> save(@RequestBody CoinDTO dto, @RequestParam String username) {
		if (username == null)
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
			                     .build();

		return ResponseEntity.ok(service.save(dto));
	}

	@PutMapping("/{id}")
	@PreAuthorize("#username == authentication.principal.user.username")
	public ResponseEntity<CoinDTO> update(@PathVariable UUID id, @RequestBody CoinDTO dto, @RequestParam String username) {
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
