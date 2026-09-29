package ro.cristiansterie.databasebackend.service;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import ro.cristiansterie.databasebackend.dto.RoleDTO;
import ro.cristiansterie.databasebackend.dto.UserDTO;
import ro.cristiansterie.databasebackend.model.RoleEntity;
import ro.cristiansterie.databasebackend.model.UserEntity;
import ro.cristiansterie.databasebackend.repository.UserRepository;
import ro.cristiansterie.databasebackend.util.converter.models.RoleModelConverter;
import ro.cristiansterie.databasebackend.util.converter.models.UserModelConverter;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
	@Mock UserRepository repository;
	@Mock UserModelConverter converter;
	@Mock RoleModelConverter roleConverter;
	@Mock PasswordEncoder passwordEncoder;
	@Mock RoleService roleService;

	private UserService service;

	@BeforeEach
	void setUp() {
		service = new UserService(repository, converter, roleConverter, passwordEncoder, roleService);
	}

	@Test
	void findsUsersByIdAndUsernameAndReturnsNullWhenMissing() {
		var id = UUID.randomUUID();
		var entity = user("alice", "encoded", "alice@example.test");
		var dto = dto(id, "alice", "encoded", Set.of());
		when(repository.findById(id)).thenReturn(Optional.of(entity));
		when(repository.findByUsername("alice")).thenReturn(Optional.of(entity));
		when(converter.toDto(entity)).thenReturn(dto);

		assertThat(service.findById(id)).isEqualTo(dto);
		assertThat(service.findByUsername("alice")).isEqualTo(dto);
		assertThat(service.findById(UUID.randomUUID())).isNull();
		assertThat(service.findByUsername("missing")).isNull();
		verify(converter, times(2)).toDto(entity);
	}

	@Test
	void convertsAllUsers() {
		var first = user("alice", "encoded-a", "alice@example.test");
		var second = user("bob", "encoded-b", "bob@example.test");
		var expected = List.of(dto(UUID.randomUUID(), "alice", "encoded-a", Set.of()),
				dto(UUID.randomUUID(), "bob", "encoded-b", Set.of()));
		when(repository.findAll()).thenReturn(List.of(first, second));
		when(converter.toDtoList(List.of(first, second))).thenReturn(expected);

		assertThat(service.findAll()).containsExactlyElementsOf(expected);
	}

	@Test
	void savesUserAndResolvesRequestedRoles() {
		var id = UUID.randomUUID();
		var roleDto = new RoleDTO(UUID.randomUUID(), "ADMIN", "Administrators");
		var request = dto(id, "alice", "password", Set.of(roleDto));
		var entity = user("alice", "password", "alice@example.test");
		var roleEntity = new RoleEntity(roleDto.id(), roleDto.name(), roleDto.description());
		var unrequestedRole = new RoleEntity(UUID.randomUUID(), "VIEWER", "Read-only access");
		when(converter.toEntity(request)).thenReturn(entity);
		when(roleService.findAllRoles()).thenReturn(List.of(roleDto,
				new RoleDTO(unrequestedRole.getId(), unrequestedRole.getName(), unrequestedRole.getDescription())));
		when(roleConverter.toEntityList(any())).thenReturn(List.of(roleEntity, unrequestedRole));
		when(repository.save(entity)).thenReturn(entity);
		when(converter.toDto(entity)).thenReturn(request);

		assertThat(service.save(request)).isEqualTo(request);
		assertThat(entity.getRoles()).containsExactly(roleEntity);
		verify(repository).save(entity);
	}

	@Test
	void updatesProfileEncodesNonblankPasswordAndReplacesRoles() {
		var id = UUID.randomUUID();
		var existing = user("old-name", "old-hash", "old@example.test");
		var roleDto = new RoleDTO(UUID.randomUUID(), "EDITOR", "Editors");
		var request = new UserDTO(id, "new-name", "new-password", "new@example.test", null,
				Set.of(roleDto), null);
		var roleEntity = new RoleEntity(roleDto.id(), roleDto.name(), roleDto.description());
		when(repository.findById(id)).thenReturn(Optional.of(existing));
		when(passwordEncoder.encode("new-password")).thenReturn("new-hash");
		when(roleService.findAllRoles()).thenReturn(List.of(roleDto));
		when(roleConverter.toEntityList(List.of(roleDto))).thenReturn(List.of(roleEntity));
		when(repository.save(existing)).thenReturn(existing);
		when(converter.toDto(existing)).thenReturn(request);

		assertThat(service.update(id, request)).isEqualTo(request);
		assertThat(existing.getUsername()).isEqualTo("new-name");
		assertThat(existing.getEmail()).isEqualTo("new@example.test");
		assertThat(existing.getPassword()).isEqualTo("new-hash");
		assertThat(existing.getRoles()).containsExactly(roleEntity);
		verify(passwordEncoder).encode("new-password");
	}

	@Test
	void leavesPasswordAndRolesUnchangedWhenUpdateOmitsThem() {
		var id = UUID.randomUUID();
		var existingRole = new RoleEntity(UUID.randomUUID(), "USER", "Users");
		var existing = user("alice", "old-hash", "old@example.test");
		existing.setRoles(Set.of(existingRole));
		var request = new UserDTO(id, "alice-new", "  ", "alice-new@example.test", null, Set.of(), null);
		when(repository.findById(id)).thenReturn(Optional.of(existing));
		when(repository.save(existing)).thenReturn(existing);
		when(converter.toDto(existing)).thenReturn(request);

		service.update(id, request);

		assertThat(existing.getPassword()).isEqualTo("old-hash");
		assertThat(existing.getRoles()).containsExactly(existingRole);
		verifyNoInteractions(passwordEncoder, roleService, roleConverter);
	}

	@Test
	void updateRejectsInvalidIdAndMissingUser() {
		var request = dto(UUID.randomUUID(), "alice", "password", Set.of());
		assertThatThrownBy(() -> service.update(null, request))
				.isInstanceOf(EntityNotFoundException.class).hasMessage("No user to update");
		assertThatThrownBy(() -> service.update(UUID.randomUUID(), null))
				.isInstanceOf(EntityNotFoundException.class).hasMessage("No user to update");

		var id = UUID.randomUUID();
		when(repository.findById(id)).thenReturn(Optional.empty());
		assertThatThrownBy(() -> service.update(id, request))
				.isInstanceOf(EntityNotFoundException.class).hasMessage("User not found to update");
	}

	@Test
	void deletesExistingUserAndRejectsMissingUser() {
		var id = UUID.randomUUID();
		when(repository.existsById(id)).thenReturn(true);

		assertThat(service.delete(id)).isTrue();
		verify(repository).deleteById(id);

		var missingId = UUID.randomUUID();
		when(repository.existsById(missingId)).thenReturn(false);
		assertThatThrownBy(() -> service.delete(missingId))
				.isInstanceOf(EntityNotFoundException.class)
				.hasMessage("User not found with id: " + missingId);
		verify(repository, never()).deleteById(missingId);
	}

	private static UserEntity user(String username, String password, String email) {
		return new UserEntity(username, password, email, Set.of());
	}

	private static UserDTO dto(UUID id, String username, String password, Set<RoleDTO> roles) {
		return new UserDTO(id, username, password, username + "@example.test", null, roles, null);
	}
}
