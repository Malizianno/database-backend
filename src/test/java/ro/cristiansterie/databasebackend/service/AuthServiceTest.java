package ro.cristiansterie.databasebackend.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import ro.cristiansterie.databasebackend.dto.BiometricLoginRequestDTO;
import ro.cristiansterie.databasebackend.dto.BiometricRegisterRequestDTO;
import ro.cristiansterie.databasebackend.dto.UserPassLoginRequestDTO;
import ro.cristiansterie.databasebackend.security.jwt.JwtUtils;
import ro.cristiansterie.databasebackend.security.tokens.BiometricsAuthenticationToken;
import ro.cristiansterie.databasebackend.security.userdetails.BiometricsHelperService;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {
	@Mock AuthenticationManager authenticationManager;
	@Mock JwtUtils jwtUtils;
	@Mock BiometricsHelperService biometricsHelperService;

	private AuthService service;
	private Authentication authenticated;

	@BeforeEach
	void setUp() {
		service = new AuthService(authenticationManager, jwtUtils, biometricsHelperService);
		authenticated = new UsernamePasswordAuthenticationToken(
				"alice", "ignored", List.of(new SimpleGrantedAuthority("ROLE_USER")));
	}

	@Test
	void authenticatesUsernameAndPasswordAndReturnsTokenAndAuthorities() {
		stubSuccessfulAuthentication();
		var response = service.authenticateUserPass(new UserPassLoginRequestDTO("alice", "secret"));

		assertThat(response.username()).isEqualTo("alice");
		assertThat(response.token()).isEqualTo("jwt-token");
		assertThat(response.authorities().size()).isEqualTo(1);
		assertThat(response.authorities().iterator().next().getAuthority()).isEqualTo("ROLE_USER");
		var token = org.mockito.ArgumentCaptor.forClass(Authentication.class);
		verify(authenticationManager).authenticate(token.capture());
		assertThat(token.getValue()).isInstanceOf(UsernamePasswordAuthenticationToken.class);
		assertThat(token.getValue().getPrincipal()).isEqualTo("alice");
		assertThat(token.getValue().getCredentials()).isEqualTo("secret");
		verify(jwtUtils).generateToken(authenticated);
	}

	@Test
	void verifiesBiometricSignatureBeforeAuthenticatingAndReturnsToken() {
		stubSuccessfulAuthentication();
		var request = new BiometricLoginRequestDTO("alice", "challenge", "signature");
		when(biometricsHelperService.verify("alice", "challenge", "signature")).thenReturn(true);

		var response = service.authenticateBiometrics(request);

		assertThat(response.username()).isEqualTo("alice");
		assertThat(response.token()).isEqualTo("jwt-token");
		var token = org.mockito.ArgumentCaptor.forClass(Authentication.class);
		verify(authenticationManager).authenticate(token.capture());
		assertThat(token.getValue()).isInstanceOf(BiometricsAuthenticationToken.class);
		verify(biometricsHelperService).verify("alice", "challenge", "signature");
		verify(jwtUtils).generateToken(authenticated);
	}

	@Test
	void rejectsInvalidBiometricSignatureWithoutCallingAuthenticationManager() {
		when(biometricsHelperService.verify("alice", "challenge", "bad-signature")).thenReturn(false);

		assertThatThrownBy(() -> service.authenticateBiometrics(
				new BiometricLoginRequestDTO("alice", "challenge", "bad-signature")))
				.isInstanceOf(AuthenticationServiceException.class)
				.hasMessage("Signature verification failed.");

		verify(authenticationManager, never()).authenticate(any(Authentication.class));
		verify(jwtUtils, never()).generateToken(any(Authentication.class));
	}

	@Test
	void delegatesBiometricRegistrationAndChallengeRequests() {
		when(biometricsHelperService.register("alice", "public-key")).thenReturn(true);
		when(biometricsHelperService.getChallenge("alice")).thenReturn("challenge");

		assertThat(service.registerBiometricAuthentication(new BiometricRegisterRequestDTO("alice", "public-key"))).isTrue();
		assertThat(service.getChallengeBiometricsAuthentication("alice")).isEqualTo("challenge");

		verify(biometricsHelperService).register("alice", "public-key");
		verify(biometricsHelperService).getChallenge("alice");
	}

	private void stubSuccessfulAuthentication() {
		when(authenticationManager.authenticate(any(Authentication.class))).thenReturn(authenticated);
		when(jwtUtils.generateToken(authenticated)).thenReturn("jwt-token");
	}
}
