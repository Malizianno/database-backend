package ro.cristiansterie.databasebackend.service;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import ro.cristiansterie.databasebackend.dto.RoleDTO;
import ro.cristiansterie.databasebackend.dto.UserDTO;
import ro.cristiansterie.databasebackend.model.RoleEntity;
import ro.cristiansterie.databasebackend.model.UserEntity;
import ro.cristiansterie.databasebackend.repository.UserRepository;
import ro.cristiansterie.databasebackend.util.converter.models.RoleModelConverter;
import ro.cristiansterie.databasebackend.util.converter.models.UserModelConverter;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

	@Mock
	private UserRepository userRepository;
	@Mock
	private PasswordEncoder passwordEncoder;
	@Mock
	private RoleModelConverter roleConverter;
	@Mock
	private UserModelConverter converter;
	@Mock
	private RoleService roleService;

	@InjectMocks
	private UserService service;

	@BeforeEach
	void setUp() {
		service = new UserService(userRepository, converter, roleConverter, passwordEncoder, roleService);
	}

	@Test
	@Transactional
	void testFindById() {
		// insert
		var userUUID = UUID.randomUUID();
		Set<RoleEntity> roles = new HashSet<>();
		roles.add(new RoleEntity(UUID.randomUUID(), "ADMIN", "can do everything"));
		UserEntity user = new UserEntity("admin", "12345", "admin@databaseproject.ro", roles);

		Set<RoleDTO> rolesDTO = new HashSet<>();
		rolesDTO.add(new RoleDTO(UUID.randomUUID(), "ADMIN", "can do everything"));
		UserDTO userDTO = new UserDTO(userUUID, user.getUsername(), user.getPassword(), user.getEmail(), null, rolesDTO, List.of(new SimpleGrantedAuthority("ADMIN")));

		when(userRepository.findById(userUUID)).thenReturn(Optional.of(user));
		when(converter.toDto(any())).thenReturn(userDTO);

		// read
		UserDTO found = service.findById(userUUID);

		// assert
		assertThat(found.username()).isEqualTo(user.getUsername());
	}

	@Test
	@Transactional
	void testFindAll() {
		// insert
		Set<RoleEntity> roles = new HashSet<>();
		roles.add(new RoleEntity(UUID.randomUUID(), "ADMIN", "can do everything"));
		UserEntity user1 = new UserEntity("admin", "12345", "admin@databaseproject", roles);
		UserEntity user2 = new UserEntity("admin2", "12345", "admin2@databaseproject", roles);

		Set<RoleDTO> rolesDTO = new HashSet<>();
		rolesDTO.add(new RoleDTO(UUID.randomUUID(), "ADMIN", "can do everything"));
		UserDTO user1DTO = new UserDTO(UUID.randomUUID(), user1.getUsername(), user1.getPassword(), user1.getEmail(), null, rolesDTO, List.of(new SimpleGrantedAuthority("ADMIN")));
		UserDTO user2DTO = new UserDTO(UUID.randomUUID(), user2.getUsername(), user2.getPassword(), user2.getEmail(), null, rolesDTO, List.of(new SimpleGrantedAuthority("ADMIN")));

		when(userRepository.findAll()).thenReturn(List.of(user1, user2));
		when(converter.toDtoList(any())).thenReturn(List.of(user1DTO, user2DTO));

		// read
		List<UserDTO> found = service.findAll();

		// assert
		assertThat(found.size()).isEqualTo(2);
		assertThat(found.get(0)
		                .username()).isEqualTo(user1.getUsername());
	}

	@Test
	@Transactional
	void testSave() {
		// insert/check
		Set<RoleEntity> roles = new HashSet<>();
		roles.add(new RoleEntity(UUID.randomUUID(), "ADMIN", "can do everything"));
		Set<RoleDTO> rolesDTO = new HashSet<>();
		rolesDTO.add(new RoleDTO(UUID.randomUUID(), "ADMIN", "can do everything"));

		UserEntity user = new UserEntity("admin", "12345", "admin@databaseproject", roles);
		UserDTO userDTO = new UserDTO(UUID.randomUUID(), user.getUsername(), user.getPassword(), user.getEmail(), null, rolesDTO, List.of(new SimpleGrantedAuthority("ADMIN")));
		when(userRepository.save(any())).thenReturn(user);
		when(converter.toEntity(any())).thenReturn(user);
		when(converter.toDto(any())).thenReturn(userDTO);
		when(roleService.findAllRoles()).thenReturn(rolesDTO.stream()
		                                                    .toList());

		// read/insert
		UserDTO saved = service.save(userDTO);

		// assert
		assertThat(saved.username()).isEqualTo(user.getUsername());
	}

	@Test
	@Transactional
	void testUpdate() {
		// insert
		Set<RoleEntity> roles = new HashSet<>();
		RoleEntity role = new RoleEntity(null, "ADMIN", "can do everything");
		roles.add(role);

		Set<RoleDTO> rolesDTO = new HashSet<>();
		RoleDTO roleDTO = new RoleDTO(UUID.randomUUID(), "ADMIN", "can do everything");
		rolesDTO.add(roleDTO);

		var userUUID = UUID.randomUUID();
		UserEntity user = new UserEntity("admin", "12345", "admin@databaseproject", roles);
		UserDTO userDTO = new UserDTO(userUUID, user.getUsername(), user.getPassword(), user.getEmail(), null, rolesDTO, List.of());
		when(userRepository.findById(userUUID)).thenReturn(Optional.of(user));
		when(passwordEncoder.encode(any())).thenReturn("12345");
		when(roleConverter.toEntityList(any())).thenReturn(List.of(role));
		when(converter.toDto(any())).thenReturn(userDTO);
		when(roleService.findAllRoles()).thenReturn(rolesDTO.stream()
		                                                    .toList());

		// read
		UserDTO updated = service.update(userUUID, userDTO);

		// assert
		assertThat(updated.username()).isEqualTo(user.getUsername());
	}

	@Test
	void leavesPasswordAndRolesUnchangedWhenUpdateOmitsThem() {
		var id = UUID.randomUUID();
		var existingRole = new RoleEntity(UUID.randomUUID(), "USER", "Users");
		var existing = new UserEntity("alice", "old-hash", "old@example.test", Set.of(existingRole));
		var request = new UserDTO(id, "alice-new", "  ", "alice-new@example.test", null, Set.of(), null);
		when(userRepository.findById(id)).thenReturn(Optional.of(existing));
		when(userRepository.save(existing)).thenReturn(existing);
		when(converter.toDto(existing)).thenReturn(request);

		service.update(id, request);

		assertThat(existing.getPassword()).isEqualTo("old-hash");
		assertThat(existing.getRoles()).containsExactly(existingRole);
		verifyNoInteractions(passwordEncoder, roleService, roleConverter);
	}

	@Test
	void updateRejectsInvalidIdAndMissingUser() {
		var request = new UserDTO(UUID.randomUUID(), "mike", "password", null, null, null, null);
		assertThatThrownBy(() -> service.update(null, request))
				.isInstanceOf(EntityNotFoundException.class)
				.hasMessage("No user to update");
		assertThatThrownBy(() -> service.update(UUID.randomUUID(), null))
				.isInstanceOf(EntityNotFoundException.class)
				.hasMessage("No user to update");

		var id = UUID.randomUUID();
		when(userRepository.findById(id)).thenReturn(Optional.empty());
		assertThatThrownBy(() -> service.update(id, request))
				.isInstanceOf(EntityNotFoundException.class)
				.hasMessage("User not found to update");
	}

	@Test
	void deletesExistingUserAndRejectsMissingUser() {
		var id = UUID.randomUUID();
		when(userRepository.existsById(id)).thenReturn(true);

		assertThat(service.delete(id)).isTrue();
		verify(userRepository).deleteById(id);

		var missingId = UUID.randomUUID();
		when(userRepository.existsById(missingId)).thenReturn(false);
		assertThatThrownBy(() -> service.delete(missingId))
				.isInstanceOf(EntityNotFoundException.class)
				.hasMessage("User not found with id: " + missingId);
		verify(userRepository, never()).deleteById(missingId);
	}
}
