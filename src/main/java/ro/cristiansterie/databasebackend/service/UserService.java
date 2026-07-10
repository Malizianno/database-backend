package ro.cristiansterie.databasebackend.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.cristiansterie.databasebackend.dto.UserDTO;
import ro.cristiansterie.databasebackend.model.UserEntity;
import ro.cristiansterie.databasebackend.repository.UserRepository;
import ro.cristiansterie.databasebackend.util.converter.models.RoleModelConverter;
import ro.cristiansterie.databasebackend.util.converter.models.UserModelConverter;

import java.util.HashSet;
import java.util.List;

@Service
@Slf4j
public class UserService {

	private final UserRepository repo;
	private final UserModelConverter converter;
	private final RoleModelConverter roleConverter;
	private final PasswordEncoder passwordEncoder;

	public UserService(UserRepository repo, UserModelConverter converter, RoleModelConverter roleConverter, PasswordEncoder passwordEncoder) {
		this.repo = repo;
		this.converter = converter;
		this.roleConverter = roleConverter;
		this.passwordEncoder = passwordEncoder;
	}

	public List<UserDTO> getAll() {
		return converter.toDtoList(repo.findAll());
	}

	public UserDTO getById(Long id) {
		return converter.toDto(repo.findById(id)
		                           .orElse(null));
	}

	public UserDTO getByUsername(String username) {
		return converter.toDto(repo.findByUsername(username)
		                           .orElse(null));
	}

	@Transactional
	public UserDTO addUser(UserDTO userDTO) {
		return converter.toDto(repo.save(converter.toEntity(userDTO)));
	}

	@Transactional
	public UserDTO updateUser(UserDTO userDTO) {
		if (userDTO == null || userDTO.id() == null) {
			throw new EntityNotFoundException("No user to update");
		}

		UserEntity user = repo.findById(userDTO.id())
		                      .orElseThrow(() -> new EntityNotFoundException("User not found"));

		// Update the fields. Hibernate tracks these changes automatically ("Dirty Checking")
		user.setUsername(userDTO.username());
		user.setEmail(userDTO.email());

		if (userDTO.password() != null && !userDTO.password()
		                                          .isBlank()) {
			user.setPassword(passwordEncoder.encode(userDTO.password()));
		}

		// Handle relationships safely (Collections should be cleared & added, not overwritten)
		if (userDTO.roles() != null) {
			user.getRoles()
			    .clear();
			user.getRoles()
			    .addAll(new HashSet<>(roleConverter.toEntityList(userDTO.roles())));
		}

		// NO NEED TO CALL userRepository.save() HERE!
		// When the @Transactional method finishes cleanly, Hibernate automatically flushes
		// the dirty changes and commits a highly optimized SQL UPDATE statement.
		return converter.toDto(user);
	}

	@Transactional
	public Boolean deleteById(Long id) {
		if (!repo.existsById(id)) {
			throw new EntityNotFoundException("User not found with id: " + id);
		}

		repo.deleteById(id);
		return true;
	}
}
