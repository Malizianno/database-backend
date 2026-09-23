package ro.cristiansterie.databasebackend.security.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.NonNull;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import ro.cristiansterie.databasebackend.util.AppConstants;

import java.io.IOException;

@Component
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	private final JwtUtils jwtUtils;
	private final UserDetailsService userService;
	private final AuthenticationEntryPoint authenticationEntryPoint;

	public JwtAuthenticationFilter(
			JwtUtils jwtUtils,
			UserDetailsService userService,
			AuthenticationEntryPoint authenticationEntryPoint
	) {
		this.jwtUtils = jwtUtils;
		this.userService = userService;
		this.authenticationEntryPoint = authenticationEntryPoint;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain) throws ServletException, IOException {
		SecurityContextHolder.clearContext();

		String authHeader = request.getHeader(AppConstants.AUTH_HEADER_NAME);

		// if no authorization forward the request
		if (authHeader == null || authHeader.isBlank()) {
			filterChain.doFilter(request, response);
			return;
		}

		if (!authHeader.startsWith(AppConstants.AUTH_HEADER_VALUE_PREFIX)) {
			return401(request, response);
			return;
		}

		String token = authHeader.substring(AppConstants.AUTH_HEADER_VALUE_PREFIX.length())
		                         .trim();

		if (token.isBlank() || !jwtUtils.validateToken(token)) {
			return401(request, response);
			return;
		}

		String username = jwtUtils.getUsernameFromToken(token);

		if (username.isBlank()) {
			return401(request, response);
			return;
		}

		// Fetch full UserDetails from DB/Session Wrapper
		try {
			UserDetails userDetails = userService.loadUserByUsername(username);

			// Authenticate the user manually inside the security context
			UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
					userDetails,
					null,
					userDetails.getAuthorities());

			authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
			SecurityContextHolder.getContext()
			                     .setAuthentication(authentication);

			filterChain.doFilter(request, response);
		} catch (
				UsernameNotFoundException unfe) {
			log.error(AppConstants.USERNAME_NOT_FOUND_MESSAGE, unfe);
			return401(request, response);
		} catch (
				AuthenticationException ae) {
			return401(request, response);
		}
	}

	private void return401(HttpServletRequest request, @NonNull HttpServletResponse response) throws ServletException, IOException {
		SecurityContextHolder.clearContext();

		authenticationEntryPoint.commence(
				request,
				response,
				new BadCredentialsException(AppConstants.INVALID_OR_EXPIRED_JWT_TOKEN));
	}
}
