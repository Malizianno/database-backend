package ro.cristiansterie.databasebackend.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ro.cristiansterie.databasebackend.dto.UserDTO;
import ro.cristiansterie.databasebackend.service.UserService;

import java.util.List;
import java.util.Set;
import java.util.UUID;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class UserControllerTest {
	private static final UUID ID = UUID.fromString("aca76f72-fdce-437c-88ed-8ffcffc891de");
	private final UserService service = mock(UserService.class);
	private MockMvc mvc;

	@BeforeEach
	void setUp() {
		mvc = MockMvcBuilders.standaloneSetup(new UserController(service)).build();
	}

	@Test
	void listUsersReturnsServiceResult() throws Exception {
		var user = user("alice", "secret");
		when(service.findAll()).thenReturn(List.of(user));

		mvc.perform(get("/users/"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$[0].username").value("alice"));

		verify(service).findAll();
	}

	@Test
	void findUserByIdDelegatesPathId() throws Exception {
		var user = user("alice", "secret");
		when(service.findById(ID)).thenReturn(user);

		mvc.perform(get("/users/{id}", ID))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.username").value("alice"));

		verify(service).findById(ID);
	}

	@Test
	void findByUsernameDelegatesQueryParameter() throws Exception {
		var user = user("alice", "secret");
		when(service.findByUsername("alice")).thenReturn(user);

		mvc.perform(get("/users/profile").param("username", "alice"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.username").value("alice"));

		verify(service).findByUsername("alice");
	}

	@Test
	void saveDelegatesJsonBody() throws Exception {
		var request = user("alice", "secret");
		when(service.save(request)).thenReturn(request);

		mvc.perform(post("/users/").contentType("application/json").content(userJson("alice", "secret")))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.username").value("alice"))
				.andExpect(jsonPath("$.password").doesNotExist());

		verify(service).save(request);
	}

	@Test
	void updateDelegatesPathIdAndJsonBody() throws Exception {
		var request = user("alice", "new-secret");
		when(service.update(ID, request)).thenReturn(request);

		mvc.perform(put("/users/{id}", ID).contentType("application/json").content(userJson("alice", "new-secret")))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.username").value("alice"));

		verify(service).update(ID, request);
	}

	@Test
	void deleteReturnsServiceResult() throws Exception {
		when(service.delete(ID)).thenReturn(true);

		mvc.perform(delete("/users/{id}", ID))
				.andExpect(status().isOk())
				.andExpect(content().string("true"));

		verify(service).delete(ID);
	}

	private static UserDTO user(String username, String password) {
		return new UserDTO(null, username, password, username + "@example.test", null, Set.of(), null);
	}

	private static String userJson(String username, String password) {
		return "{\"username\":\"" + username + "\",\"password\":\"" + password + "\",\"email\":\"" + username + "@example.test\",\"roles\":[]}";
	}
}
