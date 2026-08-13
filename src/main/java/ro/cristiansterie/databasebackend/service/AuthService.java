package ro.cristiansterie.databasebackend.service;

import jakarta.transaction.Transactional;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import ro.cristiansterie.databasebackend.dto.LoginRequestDTO;
import ro.cristiansterie.databasebackend.dto.LoginResponseDTO;
import ro.cristiansterie.databasebackend.security.jwt.JwtUtils;

@Service
public class AuthService {
	private final AuthenticationManager authenticationManager;
	private final JwtUtils jwtUtils;

	public AuthService(AuthenticationManager authenticationManager, JwtUtils jwtUtils) {
		this.authenticationManager = authenticationManager;
		this.jwtUtils = jwtUtils;
	}

	@Transactional
	public LoginResponseDTO authenticate(LoginRequestDTO loginRequest) {
		// Triggers the background UserDetailsService lookup and validation checks automatically
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.username(), loginRequest.password())
		);

		// return token
		return new LoginResponseDTO(authentication.getName(), authentication.getAuthorities(), jwtUtils.generateToken(authentication));
	}
}
