package ro.cristiansterie.databasebackend.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.ProviderNotFoundException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ro.cristiansterie.databasebackend.dto.ErrorResponseDTO;

@RestControllerAdvice
@SuppressWarnings("unused")
public class GlobalExceptionHandler {

	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<ErrorResponseDTO> handleIllegalArgument(IllegalArgumentException ex) {
		return new ResponseEntity<>(
				new ErrorResponseDTO(
						ex.getMessage(),
						"NOT_ACCEPTABLE",
						HttpStatus.NOT_ACCEPTABLE.value()
				), HttpStatus.NOT_ACCEPTABLE);
	}

	@ExceptionHandler({
			ProviderNotFoundException.class,
			BadCredentialsException.class,
			UsernameNotFoundException.class,
			AuthenticationServiceException.class
	})
	public ResponseEntity<ErrorResponseDTO> handleUnauthorized(AuthenticationException ex) {
		return new ResponseEntity<>(
				new ErrorResponseDTO(
						ex.getMessage(),
						"UNAUTHORIZED",
						HttpStatus.UNAUTHORIZED.value()
				), HttpStatus.UNAUTHORIZED);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorResponseDTO> handleException(Exception ex) {
		return new ResponseEntity<>(
				new ErrorResponseDTO(
						ex.getMessage(),
						"UNKNOWN EXCEPTION",
						HttpStatus.BAD_REQUEST.value()
				), HttpStatus.BAD_REQUEST);
	}
}
