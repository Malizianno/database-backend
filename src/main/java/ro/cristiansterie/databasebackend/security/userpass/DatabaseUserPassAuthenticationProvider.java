package ro.cristiansterie.databasebackend.security.userpass;

import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.NonNull;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Objects;

@Slf4j
public class DatabaseUserPassAuthenticationProvider implements AuthenticationProvider {
    private final DatabaseUserPassUserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;

    public DatabaseUserPassAuthenticationProvider(DatabaseUserPassUserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {
        this.userDetailsService = userDetailsService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
//		log.info("DUPAP:: Authenticating user {}", authentication.getName());
        String username = authentication.getName();
        String password = Objects.requireNonNull(authentication.getCredentials()).toString();

        UserDetails userDetails = userDetailsService.loadUserByUsername(username);

        // Validating the password securely
        if (passwordEncoder.matches(password, userDetails.getPassword())) {
			log.info("DUPAP:: User {} authenticated successfully with roles {}", username, userDetails.getAuthorities());
            return UsernamePasswordAuthenticationToken.authenticated(
                    userDetails,
                    null,
                    userDetails.getAuthorities()
            );
        } else {
			log.error("DUPAP:: Invalid username or password");
            throw new BadCredentialsException("Invalid username or password.");
        }
    }

    @Override
    public boolean supports(@NonNull Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
