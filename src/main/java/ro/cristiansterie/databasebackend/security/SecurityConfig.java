package ro.cristiansterie.databasebackend.security;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import ro.cristiansterie.databasebackend.security.jwt.JwtAuthenticationFilter;
import ro.cristiansterie.databasebackend.security.log.RequestLogger;
import ro.cristiansterie.databasebackend.security.providers.BiometricsAuthenticationProvider;
import ro.cristiansterie.databasebackend.security.providers.DatabaseUserPassAuthenticationProvider;
import ro.cristiansterie.databasebackend.util.AppConstants;

import java.util.List;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {
	private final BiometricsAuthenticationProvider biometricsAuthenticationProvider;
	private final DatabaseUserPassAuthenticationProvider databaseUserPassAuthenticationProvider;

	@Bean
	public AuthenticationManager authenticationManager(
			DatabaseUserPassAuthenticationProvider databaseUserPassAuthenticationProvider,
			BiometricsAuthenticationProvider biometricsAuthenticationProvider) {
		return new ProviderManager(List.of(databaseUserPassAuthenticationProvider, biometricsAuthenticationProvider));
	}

	@Bean
	public AuthenticationEntryPoint authenticationEntryPoint() {
		return (request, response, authException) -> {
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			response.setContentType(AppConstants.RESPONSE_TYPE);
			response.setCharacterEncoding(AppConstants.RESPONSE_CHARACTER_ENCODING);
			response.getWriter()
			        .write(AppConstants.INVALID_OR_EXPIRED_JWT_TOKEN);
		};
	}

	@Bean
	public CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration config = new CorsConfiguration();
		List<String> allowed = List.of(
				"http://localhost:4221",
				"https://database-frontend-muhs.onrender.com",
				"https://www.cristiansterie.dev"
		);

		config.setAllowedOrigins(allowed);
		config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
		config.setAllowedHeaders(List.of("*"));
		config.setAllowCredentials(true);

		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", config);

		return source;
	}

	@Bean
	public SecurityFilterChain securityFilterChain(
			HttpSecurity http,
			JwtAuthenticationFilter jwtAuthenticationFilter,
			RequestLogger requestLogger
	) {
		var excludedEndpoints = new String[]{
				"/auth/login",
				"/actuator/health",
				"/auth/bio",
				"/auth/bio/challenge"
		};

		return http
				.csrf(AbstractHttpConfigurer::disable)
				.cors(cors -> cors.configurationSource(corsConfigurationSource()))
				.exceptionHandling(ex -> ex.authenticationEntryPoint(authenticationEntryPoint()))
				.authorizeHttpRequests(auth -> auth
						.requestMatchers(excludedEndpoints)
						.permitAll()
						.anyRequest()
						.authenticated()
				)
				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.authenticationManager(authenticationManager(databaseUserPassAuthenticationProvider, biometricsAuthenticationProvider))
				.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
				.addFilterBefore(requestLogger, UsernamePasswordAuthenticationFilter.class)
				.build();
	}
}

