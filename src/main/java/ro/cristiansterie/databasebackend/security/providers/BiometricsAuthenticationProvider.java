package ro.cristiansterie.databasebackend.security.providers;

import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.NonNull;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import ro.cristiansterie.databasebackend.security.tokens.BiometricsAuthenticationToken;

@Component
@Slf4j
public class BiometricsAuthenticationProvider implements AuthenticationProvider {
	
	private final UserDetailsService userDetailsService;

	public BiometricsAuthenticationProvider(UserDetailsService userDetailsService) {
		this.userDetailsService = userDetailsService;
	}

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		log.info("Authenticating user {}", authentication.getName());

		if (authentication instanceof BiometricsAuthenticationToken bat) {
			String username = bat.getName();
			String originalChallenge = bat.getOriginalChallenge();
			String signature = bat.getSignature();

			UserDetails userDetails = userDetailsService.loadUserByUsername(username);
			log.info("User {} authenticated successfully with roles {}", username, userDetails.getAuthorities());

			return BiometricsAuthenticationToken.authenticated(
					username,
					originalChallenge,
					signature,
					userDetails.getAuthorities()
			);
		}

		throw new ProviderNotFoundException("Authentication not recognized as biometrics.");
	}


	@Override
	public boolean supports(@NonNull Class<?> authentication) {
		return BiometricsAuthenticationToken.class.isAssignableFrom(authentication);
	}
}
