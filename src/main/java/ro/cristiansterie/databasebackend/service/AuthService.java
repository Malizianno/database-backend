package ro.cristiansterie.databasebackend.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import ro.cristiansterie.databasebackend.dto.BiometricLoginRequestDTO;
import ro.cristiansterie.databasebackend.dto.BiometricRegisterRequestDTO;
import ro.cristiansterie.databasebackend.dto.LoginResponseDTO;
import ro.cristiansterie.databasebackend.dto.UserPassLoginRequestDTO;
import ro.cristiansterie.databasebackend.security.jwt.JwtUtils;
import ro.cristiansterie.databasebackend.security.tokens.BiometricsAuthenticationToken;
import ro.cristiansterie.databasebackend.security.userdetails.BiometricsHelperService;

@Service
@RequiredArgsConstructor
public class AuthService {
	private final AuthenticationManager authenticationManager;
	private final JwtUtils jwtUtils;
	private final BiometricsHelperService biometricsHelperService;

	@Transactional
	public LoginResponseDTO authenticateUserPass(@Validated UserPassLoginRequestDTO loginRequest) {
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(
						loginRequest.username(),
						loginRequest.password())
		);

		return new LoginResponseDTO(
				authentication.getName(),
				authentication.getAuthorities(),
				jwtUtils.generateToken(authentication));
	}

	@Transactional
	public LoginResponseDTO authenticateBiometrics(@Validated BiometricLoginRequestDTO loginRequest) {
		Authentication authentication = authenticationManager.authenticate(
				validateSignature(
						new BiometricsAuthenticationToken(
								loginRequest.username(),
								loginRequest.originalChallenge(),
								loginRequest.signature())));

		return new LoginResponseDTO(
				authentication.getName(),
				authentication.getAuthorities(),
				jwtUtils.generateToken(authentication));
	}

	@Transactional
	public boolean registerBiometricAuthentication(@Validated BiometricRegisterRequestDTO registerRequest) {
		return biometricsHelperService.register(registerRequest.username(), registerRequest.key());
	}

	@Transactional
	public String getChallengeBiometricsAuthentication(@NonNull String username) {
		return biometricsHelperService.getChallenge(username);
	}

	private Authentication validateSignature(Authentication authentication) {
		if (authentication instanceof BiometricsAuthenticationToken bat && biometricsHelperService.verify(
				String.valueOf(bat.getPrincipal()),
				bat.getOriginalChallenge(),
				bat.getSignature())) {
			return authentication;
		}

		throw new AuthenticationServiceException("Signature verification failed.");
	}
}
