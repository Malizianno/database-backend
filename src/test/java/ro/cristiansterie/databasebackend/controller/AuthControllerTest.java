package ro.cristiansterie.databasebackend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ro.cristiansterie.databasebackend.dto.BiometricLoginRequestDTO;
import ro.cristiansterie.databasebackend.dto.BiometricRegisterRequestDTO;
import ro.cristiansterie.databasebackend.dto.LoginResponseDTO;
import ro.cristiansterie.databasebackend.dto.UserPassLoginRequestDTO;
import ro.cristiansterie.databasebackend.service.AuthService;

import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class AuthControllerTest {
	private final AuthService service = mock(AuthService.class);
	private final ObjectMapper objectMapper = new ObjectMapper();
	private MockMvc mvc;

	@BeforeEach
	void setUp() {
		mvc = MockMvcBuilders.standaloneSetup(new AuthController(service)).build();
	}

	@Test
	void loginDelegatesCredentialsAndReturnsLoginResponse() throws Exception {
		var request = new UserPassLoginRequestDTO("alice", "secret");
		var response = new LoginResponseDTO("alice", List.of(), "signed-token");
		when(service.authenticateUserPass(request)).thenReturn(response);

		mvc.perform(post("/auth/login").contentType("application/json").content(objectMapper.writeValueAsString(request)))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.username").value("alice"))
				.andExpect(jsonPath("$.token").value("signed-token"));

		verify(service).authenticateUserPass(request);
	}

	@Test
	void biometricLoginDelegatesRequestAndReturnsLoginResponse() throws Exception {
		var request = new BiometricLoginRequestDTO("alice", "challenge", "signature");
		var response = new LoginResponseDTO("alice", List.of(), "biometric-token");
		when(service.authenticateBiometrics(request)).thenReturn(response);

		mvc.perform(post("/auth/bio").contentType("application/json").content(objectMapper.writeValueAsString(request)))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.username").value("alice"))
				.andExpect(jsonPath("$.token").value("biometric-token"));

		verify(service).authenticateBiometrics(request);
	}

	@Test
	void biometricRegistrationDelegatesRequestAndReturnsResult() throws Exception {
		var request = new BiometricRegisterRequestDTO("alice", "public-key");
		when(service.registerBiometricAuthentication(request)).thenReturn(true);

		mvc.perform(post("/auth/bio/register").contentType("application/json").content(objectMapper.writeValueAsString(request)))
				.andExpect(status().isOk())
				.andExpect(content().string("true"));

		verify(service).registerBiometricAuthentication(request);
	}

	@Test
	void challengeRoutePassesUsernameQueryParameter() throws Exception {
		when(service.getChallengeBiometricsAuthentication("alice")).thenReturn("challenge-value");

		mvc.perform(get("/auth/bio/challenge").param("username", "alice"))
				.andExpect(status().isOk())
				.andExpect(content().string("challenge-value"));

		verify(service).getChallengeBiometricsAuthentication("alice");
	}
}
