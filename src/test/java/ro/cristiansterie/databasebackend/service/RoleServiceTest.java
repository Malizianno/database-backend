package ro.cristiansterie.databasebackend.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ro.cristiansterie.databasebackend.dto.RoleDTO;
import ro.cristiansterie.databasebackend.model.RoleEntity;
import ro.cristiansterie.databasebackend.repository.RoleRepository;
import ro.cristiansterie.databasebackend.util.converter.models.RoleModelConverter;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RoleServiceTest {
	@Mock RoleRepository repository;
	@Mock RoleModelConverter converter;
	@InjectMocks RoleService service;

	@Test
	void returnsConvertedRolesFromRepository() {
		var role = new RoleEntity(UUID.randomUUID(), "ADMIN", "Manage all data");
		var dto = new RoleDTO(role.getId(), role.getName(), role.getDescription());
		when(repository.findAll()).thenReturn(List.of(role));
		when(converter.toDtoList(List.of(role))).thenReturn(List.of(dto));

		assertThat(service.findAllRoles()).containsExactly(dto);
		verify(repository).findAll();
		verify(converter).toDtoList(List.of(role));
	}

	@Test
	void returnsEmptyListWhenThereAreNoRoles() {
		when(repository.findAll()).thenReturn(List.of());
		when(converter.toDtoList(List.of())).thenReturn(List.of());

		assertThat(service.findAllRoles()).isEmpty();
	}
}
