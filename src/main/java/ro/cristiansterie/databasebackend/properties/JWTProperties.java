package ro.cristiansterie.databasebackend.properties;

import lombok.NonNull;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "jwt")
public class JWTProperties implements InitializingBean {

	private String secretKey;
	private Long expiration;


	@Override
	public void afterPropertiesSet() throws Exception {
		if (secretKey == null || secretKey.isEmpty()) {
			throw new IllegalArgumentException("secretKey cannot be null or empty");
		}

		if (expiration == null || expiration < 60_000) {
			throw new IllegalArgumentException("expiration cannot be null or less than 60_000");
		}
	}

	public @NonNull String getSecretKey() {
		return secretKey;
	}

	public void setSecretKey(@NonNull String secretKey) {
		this.secretKey = secretKey;
	}

	public @NonNull Long getExpiration() {
		return expiration;
	}

	public void setExpiration(@NonNull Long expiration) {
		this.expiration = expiration;
	}
}
