package ro.cristiansterie.databasebackend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ro.cristiansterie.databasebackend.dto.RoleDTO;
import ro.cristiansterie.databasebackend.service.RoleService;

import java.util.List;

@RestController
@RequestMapping("/roles")
public class RoleController {
	public final RoleService service;

	public RoleController(RoleService service) {
		this.service = service;
	}

	@GetMapping("/")
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<List<RoleDTO>> findAllRoles() {
		return ResponseEntity.ok(service.findAllRoles());
	}
}
