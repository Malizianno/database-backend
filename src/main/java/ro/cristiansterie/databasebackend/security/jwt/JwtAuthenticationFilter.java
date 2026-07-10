package ro.cristiansterie.databasebackend.security.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import ro.cristiansterie.databasebackend.util.AppConstants;

import java.io.IOException;

@Component
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	@Autowired
	private JwtUtils jwtUtils;

	@Autowired
	private UserDetailsService userDetailsService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain) throws ServletException, IOException {

		String authHeader = request.getHeader("Authorization");

		if (authHeader != null && authHeader.startsWith("Bearer ")) {
			String token = authHeader.substring(7); // Remove "Bearer " prefix

			if (jwtUtils.validateToken(token)) {
				String username = jwtUtils.getUsernameFromToken(token);

				// Fetch full UserDetails from DB/Session Wrapper
				UserDetails userDetails = userDetailsService.loadUserByUsername(username);

				// Authenticate the user manually inside the security context
				UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

				authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext()
				                     .setAuthentication(authentication);
			} else {
				response = returnResponse401(response);
			}
		}

		filterChain.doFilter(request, response);
	}

	private HttpServletResponse returnResponse401(@NonNull HttpServletResponse response) {
		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

		try {
			response.getWriter()
			        .write(AppConstants.INVALID_OR_EXPIRED_JWT_TOKEN);

			return response;
		} catch (
				IOException e) {
			log.error(AppConstants.RESPONSE_WRITER_ISSUE);
		}

		return null;
	}
}
