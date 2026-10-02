package ro.cristiansterie.databasebackend.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ro.cristiansterie.databasebackend.dto.CountryDTO;
import ro.cristiansterie.databasebackend.service.CountryService;

import java.util.Set;
import java.util.UUID;

@RestController
@RequestMapping("/countries")
@RequiredArgsConstructor
public class CountryController {
	private final CountryService service;

	@GetMapping("/{id}")
	@PreAuthorize("#username == authentication.principal.user.username")
	public ResponseEntity<CountryDTO> findById(@PathVariable UUID id, @RequestParam String username) {
		if (username == null)
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
			                     .build();

		return ResponseEntity.ok(service.findById(id));
	}

	@GetMapping("/")
	@PreAuthorize("#username == authentication.principal.user.username")
	public ResponseEntity<Set<CountryDTO>> findAll(@RequestParam String username) {
		if (username == null)
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
			                     .build();

		return ResponseEntity.ok(service.findAll());
	}

	@PostMapping("/")
	@PreAuthorize("#username == authentication.principal.user.username")
	public ResponseEntity<CountryDTO> save(@RequestBody CountryDTO dto, @RequestParam String username) {
		if (username == null)
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
			                     .build();

		return ResponseEntity.ok(service.save(dto));
	}

	@PutMapping("/{id}")
	@PreAuthorize("#username == authentication.principal.user.username")
	public ResponseEntity<CountryDTO> update(@PathVariable UUID id, @RequestBody CountryDTO dto, @RequestParam String username) {
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
