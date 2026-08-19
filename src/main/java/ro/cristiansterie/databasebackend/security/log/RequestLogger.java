package ro.cristiansterie.databasebackend.security.log;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@Slf4j
public class RequestLogger extends OncePerRequestFilter {

	@Override
	protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain) throws ServletException, IOException {
		var method = request.getMethod();
		var uri = request.getRequestURI();

		if (isBusinessPath(uri)) {
			log.info("{} {}", method, uri);
		}

		filterChain.doFilter(request, response);
	}

	private boolean isBusinessPath(String uri) {
		return !uri.contains("/actuator");
	}
}
