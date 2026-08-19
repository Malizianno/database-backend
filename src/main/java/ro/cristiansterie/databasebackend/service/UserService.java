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
import java.util.UUID;

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

	@Transactional(readOnly = true)
	public List<UserDTO> findAll() {
		return converter.toDtoList(repo.findAll());
	}

	@Transactional(readOnly = true)
	public UserDTO findById(UUID id) {
		return converter.toDto(repo.findById(id)
		                           .orElse(null));
	}

	@Transactional(readOnly = true)
	public UserDTO findByUsername(String username) {
		return converter.toDto(repo.findByUsername(username)
		                           .orElse(null));
	}

	@Transactional
	public UserDTO save(UserDTO userDTO) {
		return converter.toDto(repo.save(converter.toEntity(userDTO)));
	}

	@Transactional
	public UserDTO update(UUID id, UserDTO userDTO) {
		if (userDTO == null || id == null) {
			throw new EntityNotFoundException("No user to update");
		}

		UserEntity user = repo.findById(id)
		                      .orElseThrow(() -> new EntityNotFoundException("User not found to update"));

		// Update the fields. Hibernate tracks these changes automatically ("Dirty Checking")
		user.setUsername(userDTO.username());
		user.setEmail(userDTO.email());

		if (userDTO.password() != null && !userDTO.password()
		                                          .isBlank()) {
			user.setPassword(passwordEncoder.encode(userDTO.password()));
		}

		// Handle relationships safely (Collections should be cleared & added, not overwritten)
		if (userDTO.roles() != null && !userDTO.roles()
		                                       .isEmpty()) {
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
	public Boolean delete(UUID id) {
		if (!repo.existsById(id)) {
			throw new EntityNotFoundException("User not found with id: " + id);
		}

		repo.deleteById(id);
		return true;
	}
}
