package ro.cristiansterie.databasebackend.security;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import ro.cristiansterie.databasebackend.security.jwt.JwtUtils;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ActiveProfiles("test")
class SecurityIntegrationTest {
	private MockMvc mvc;

	@Autowired
	private WebApplicationContext context;

	@Autowired
	private FilterChainProxy springSecurityFilterChain;

	@Autowired
	private JwtUtils jwtUtils;

	@BeforeEach
	void setUp() {
		mvc = MockMvcBuilders.webAppContextSetup(context)
				.addFilters(springSecurityFilterChain)
				.build();
	}

	@Test
	void healthEndpointIsPublic() throws Exception {
		mvc.perform(get("/actuator/health"))
				.andExpect(status().isOk());
	}

	@Test
	void publicAuthMappingsReachMvcWithoutAuthentication() throws Exception {
		mvc.perform(get("/auth/login"))
				.andExpect(status().isMethodNotAllowed());
		mvc.perform(get("/auth/bio"))
				.andExpect(status().isMethodNotAllowed());
	}

	@Test
	void protectedControllerRejectsRequestsWithoutJwt() throws Exception {
		mvc.perform(get("/countries/"))
				.andExpect(status().isUnauthorized());
	}

	@Test
	void rejectsMalformedBearerToken() throws Exception {
		mvc.perform(get("/countries/").header("Authorization", "Bearer invalid.jwt.signature"))
				.andExpect(status().isUnauthorized());
	}

	@Test
	void validJwtAuthenticatesAgainstUserDetailsAndAllowsProtectedRoute() throws Exception {
		var authentication = new UsernamePasswordAuthenticationToken(
				"admin",
				null,
				List.of(new SimpleGrantedAuthority("ADMIN")));
		String token = jwtUtils.generateToken(authentication);

		mvc.perform(get("/users/").header("Authorization", "Bearer " + token))
				.andExpect(status().isOk());
	}
}
