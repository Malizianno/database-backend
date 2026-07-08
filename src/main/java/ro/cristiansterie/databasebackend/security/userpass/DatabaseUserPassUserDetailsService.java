package ro.cristiansterie.databasebackend.security.userpass;

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
public class DatabaseUserPassUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public DatabaseUserPassUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public @NonNull UserDetails loadUserByUsername(@NonNull String username) throws UsernameNotFoundException {
//		log.info("DUPUDS:: Loading user by username: {}", username);
        UserEntity user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(AppConstants.USERNAME_NOT_FOUND_MESSAGE + username));

        return new DatabaseUserPassUserDetails(user);
    }
}
