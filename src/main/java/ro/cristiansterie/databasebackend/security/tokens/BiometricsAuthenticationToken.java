package ro.cristiansterie.databasebackend.security.tokens;

import lombok.Getter;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.io.Serial;
import java.util.Collection;

@Getter
public class BiometricsAuthenticationToken extends UsernamePasswordAuthenticationToken {
	@Serial
	private static final long serialVersionUID = -3194696462184782834L;

	private final @Nullable String originalChallenge;
	private final @Nullable String signature;

	public BiometricsAuthenticationToken(String username, @Nullable String originalChallenge, @Nullable String signature, @NonNull Collection<? extends GrantedAuthority> authorities) {
		super(username, signature, authorities);
		this.originalChallenge = originalChallenge;
		this.signature = signature;
		super.setAuthenticated(true);
	}

	public BiometricsAuthenticationToken(@Nullable String principal, @Nullable String originalChallenge, @Nullable String signature) {
		super(principal, signature);
		this.originalChallenge = originalChallenge;
		this.signature = signature;
		super.setAuthenticated(false);
	}

	public static BiometricsAuthenticationToken authenticated(String username, @Nullable String originalChallenge, @Nullable String signature, Collection<? extends GrantedAuthority> authorities) {
		return new BiometricsAuthenticationToken(username, originalChallenge, signature, authorities);
	}
}
