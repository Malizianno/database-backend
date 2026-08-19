package ro.cristiansterie.databasebackend.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.cristiansterie.databasebackend.dto.RoleDTO;
import ro.cristiansterie.databasebackend.repository.RoleRepository;
import ro.cristiansterie.databasebackend.util.converter.models.RoleModelConverter;

import java.util.List;

@Service
public class RoleService {
	private final RoleRepository repo;
	private final RoleModelConverter converter;

	public RoleService(RoleRepository repo, RoleModelConverter converter) {
		this.repo = repo;
		this.converter = converter;
	}

	@Transactional(readOnly = true)
	public List<RoleDTO> findAllRoles() {
		return converter.toDtoList(repo.findAll());
	}
}

