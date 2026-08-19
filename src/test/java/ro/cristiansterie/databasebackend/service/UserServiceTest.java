package ro.cristiansterie.databasebackend.service;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import ro.cristiansterie.databasebackend.dto.RoleDTO;
import ro.cristiansterie.databasebackend.dto.UserDTO;
import ro.cristiansterie.databasebackend.model.RoleEntity;
import ro.cristiansterie.databasebackend.model.UserEntity;
import ro.cristiansterie.databasebackend.repository.UserRepository;
import ro.cristiansterie.databasebackend.util.converter.models.RoleModelConverter;
import ro.cristiansterie.databasebackend.util.converter.models.UserModelConverter;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

@SpringBootTest
@ActiveProfiles("test")
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

	@InjectMocks
	private UserService service;

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
		UserDTO userDTO = new UserDTO(userUUID, user.getUsername(), user.getPassword(), user.getEmail(), rolesDTO, List.of(new SimpleGrantedAuthority("ADMIN")));

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
		UserDTO user1DTO = new UserDTO(UUID.randomUUID(), user1.getUsername(), user1.getPassword(), user1.getEmail(), rolesDTO, List.of(new SimpleGrantedAuthority("ADMIN")));
		UserDTO user2DTO = new UserDTO(UUID.randomUUID(), user2.getUsername(), user2.getPassword(), user2.getEmail(), rolesDTO, List.of(new SimpleGrantedAuthority("ADMIN")));

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
		UserDTO userDTO = new UserDTO(UUID.randomUUID(), user.getUsername(), user.getPassword(), user.getEmail(), rolesDTO, List.of(new SimpleGrantedAuthority("ADMIN")));
		when(userRepository.save(any())).thenReturn(user);
		when(converter.toEntity(any())).thenReturn(user);
		when(converter.toDto(any())).thenReturn(userDTO);

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
		UserDTO userDTO = new UserDTO(userUUID, user.getUsername(), user.getPassword(), user.getEmail(), rolesDTO, List.of(new SimpleGrantedAuthority("ADMIN")));
		when(userRepository.findById(userUUID)).thenReturn(Optional.of(user));
		when(passwordEncoder.encode(any())).thenReturn("12345");
		when(roleConverter.toEntityList(any())).thenReturn(List.of(role));
		when(converter.toDto(any())).thenReturn(userDTO);

		// read
		UserDTO updated = service.update(userUUID, userDTO);

		// assert
		assertThat(updated.username()).isEqualTo(user.getUsername());
	}

	@Test
	@Transactional
	void testDelete() {
		// insert
		var userUUID = UUID.randomUUID();
		when(userRepository.existsById(userUUID)).thenReturn(true);
		// read
		service.delete(userUUID);
		// assert
		assertThat(userRepository.findAll()
		                         .size()).isEqualTo(0);
	}
}
