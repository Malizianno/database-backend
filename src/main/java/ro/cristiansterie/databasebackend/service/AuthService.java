package ro.cristiansterie.databasebackend.service;

import jakarta.transaction.Transactional;
import org.jspecify.annotations.NonNull;
import org.springframework.security.authentication.AuthenticationManager;
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
public class AuthService {
	private final AuthenticationManager authenticationManager;
	private final JwtUtils jwtUtils;
	private final BiometricsHelperService biometricsHelperService;

	public AuthService(
			AuthenticationManager authenticationManager,
			JwtUtils jwtUtils,
			BiometricsHelperService biometricsHelperService
	) {
		this.authenticationManager = authenticationManager;
		this.jwtUtils = jwtUtils;
		this.biometricsHelperService = biometricsHelperService;
	}

	@Transactional
	public LoginResponseDTO authenticateUserPass(@Validated UserPassLoginRequestDTO loginRequest) {
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.username(), loginRequest.password())
		);

		return new LoginResponseDTO(authentication.getName(), authentication.getAuthorities(), jwtUtils.generateToken(authentication));
	}

	@Transactional
	public LoginResponseDTO authenticateBiometrics(@Validated BiometricLoginRequestDTO loginRequest) {
		Authentication authentication = authenticationManager.authenticate(
				new BiometricsAuthenticationToken(loginRequest.username(), loginRequest.originalChallenge(), loginRequest.signature())
		);

		return new LoginResponseDTO(authentication.getName(), authentication.getAuthorities(), jwtUtils.generateToken(authentication));
	}

	@Transactional
	public boolean registerBiometricAuthentication(@Validated BiometricRegisterRequestDTO registerRequest) {
		return biometricsHelperService.register(registerRequest.username(), registerRequest.key());
	}

	@Transactional
	public String getChallengeBiometricsAuthentication(@NonNull String username) {
		return biometricsHelperService.getChallenge(username);
	}
}
