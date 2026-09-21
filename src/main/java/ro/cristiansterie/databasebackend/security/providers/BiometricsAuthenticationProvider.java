package ro.cristiansterie.databasebackend.security.providers;

import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.NonNull;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.ProviderNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import ro.cristiansterie.databasebackend.security.tokens.BiometricsAuthenticationToken;
import ro.cristiansterie.databasebackend.security.utils.BiometricsHelperService;

@Component
@Slf4j
public class BiometricsAuthenticationProvider implements AuthenticationProvider {
	private final UserDetailsService userDetailsService;
	private final BiometricsHelperService helperService;

	public BiometricsAuthenticationProvider(UserDetailsService userDetailsService, BiometricsHelperService helperService) {
		this.userDetailsService = userDetailsService;
		this.helperService = helperService;
	}

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		log.info("Authenticating user {}", authentication.getName());

		if (authentication instanceof BiometricsAuthenticationToken bat) {
			String username = bat.getName();
			String originalChallenge = bat.getOriginalChallenge();
			String signature = bat.getSignature();

			UserDetails userDetails = userDetailsService.loadUserByUsername(username);

			if (isSignatureValid(authentication)) {
				log.info("User {} authenticated successfully with roles {}", username, userDetails.getAuthorities());

				return BiometricsAuthenticationToken.authenticated(
						username,
						originalChallenge,
						signature,
						userDetails.getAuthorities()
				);
			} else {
				log.error("Invalid challenge or signature");
				throw new BadCredentialsException("Invalid challenge or signature.");
			}
		}

		throw new ProviderNotFoundException("Authentication not recognized as biometrics.");
	}


	@Override
	public boolean supports(@NonNull Class<?> authentication) {
		return BiometricsAuthenticationToken.class.isAssignableFrom(authentication);
	}

	private boolean isSignatureValid(Authentication authentication) {
		return authentication instanceof BiometricsAuthenticationToken bat
				&& helperService.verify(
				String.valueOf(bat.getPrincipal()),
				bat.getOriginalChallenge(),
				bat.getSignature());
	}
}
