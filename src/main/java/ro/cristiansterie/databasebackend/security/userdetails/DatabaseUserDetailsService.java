package ro.cristiansterie.databasebackend.security.userdetails;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.NonNull;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ro.cristiansterie.databasebackend.model.UserEntity;
import ro.cristiansterie.databasebackend.repository.UserRepository;
import ro.cristiansterie.databasebackend.util.AppConstants;

@Service
@Slf4j
@RequiredArgsConstructor
public class DatabaseUserDetailsService implements UserDetailsService {

	private final UserRepository userRepository;

	@Override
	public @NonNull UserDetails loadUserByUsername(@NonNull String username) throws UsernameNotFoundException {
		UserEntity user = userRepository.findByUsername(username)
		                                .orElseThrow(() -> new UsernameNotFoundException(AppConstants.USERNAME_NOT_FOUND_MESSAGE + username));

		return new DatabaseUserDetails(user);
	}
}
