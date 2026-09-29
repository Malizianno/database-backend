package ro.cristiansterie.databasebackend.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ro.cristiansterie.databasebackend.dto.RoleDTO;
import ro.cristiansterie.databasebackend.service.RoleService;

import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class RoleControllerTest {
	private final RoleService service = mock(RoleService.class);
	private MockMvc mvc;

	@BeforeEach
	void setUp() {
		mvc = MockMvcBuilders.standaloneSetup(new RoleController(service)).build();
	}

	@Test
	void listsRolesAtMappedRoute() throws Exception {
		var role = new RoleDTO(UUID.fromString("c44a151d-5f49-43e7-8579-d13137164be6"), "ADMIN", "full access");
		when(service.findAllRoles()).thenReturn(List.of(role));

		mvc.perform(get("/roles/"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$[0].name").value("ADMIN"))
				.andExpect(jsonPath("$[0].description").value("full access"));

		verify(service).findAllRoles();
	}
}
