package ro.cristiansterie.databasebackend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.cristiansterie.databasebackend.dto.ImageDTO;
import ro.cristiansterie.databasebackend.service.ImageService;
import ro.cristiansterie.databasebackend.service.LanguageService;

import java.util.Set;

@RestController
@RequestMapping("/images")
public class ImageController {
	private final ImageService service;

	public ImageController(ImageService service) {
		this.service = service;
	}

	@GetMapping("/{id}")
	public ResponseEntity<ImageDTO> findById(@PathVariable Long id) {
		return ResponseEntity.ok(service.findById(id));
	}

	@GetMapping("/")
	public ResponseEntity<Set<ImageDTO>> findAll() {
		return ResponseEntity.ok(service.findAll());
	}

	@PostMapping("/")
	public ResponseEntity<ImageDTO> create(@RequestBody ImageDTO dto) {
		return ResponseEntity.ok(service.save(dto));
	}

	@PutMapping("/{id}")
	public ResponseEntity<ImageDTO> update(@PathVariable Long id, @RequestBody ImageDTO dto) {
		return ResponseEntity.ok(service.update(id, dto));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		service.delete(id);
		return ResponseEntity.noContent()
		                     .build();
	}
}
