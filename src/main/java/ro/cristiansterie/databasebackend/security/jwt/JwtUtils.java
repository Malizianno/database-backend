package ro.cristiansterie.databasebackend.security.jwt;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;
import ro.cristiansterie.databasebackend.properties.JWTProperties;
import ro.cristiansterie.databasebackend.util.AppConstants;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.List;

@Component
@Slf4j
public class JwtUtils {

	private final JWTProperties props;

	public JwtUtils(JWTProperties props) {
		this.props = props;
	}

	private SecretKey getSigningKey() {
		return Keys.hmacShaKeyFor(props.getSecretKey()
		                               .getBytes(StandardCharsets.UTF_8));
	}

	public String generateToken(Authentication authentication) {
		String username = authentication.getName();

		List<String> roles = authentication.getAuthorities()
		                                   .stream()
		                                   .map(GrantedAuthority::getAuthority)
		                                   .toList();

		return Jwts.builder()
		           .subject(username)
		           .claim("roles", roles)
		           .issuedAt(new Date())
		           .expiration(new Date(System.currentTimeMillis() + props.getExpiration()))
		           .signWith(getSigningKey())
		           .compact();
	}

	public String getUsernameFromToken(String token) {
		return Jwts.parser()
		           .verifyWith(getSigningKey())
		           .build()
		           .parseSignedClaims(token)
		           .getPayload()
		           .getSubject();
	}

	public boolean validateToken(String token) {
		try {
			Jwts.parser()
			    .verifyWith(getSigningKey())
			    .build()
			    .parseSignedClaims(token);
			return true;
		} catch (
				Exception e) {
			log.error(AppConstants.JWT_INVALID_TOKEN, token);
		}

		return false;
	}
}
