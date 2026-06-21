package ro.cristiansterie.database_backend.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ro.cristiansterie.database_backend.model.User;
import ro.cristiansterie.database_backend.repository.UserRepository;
import ro.cristiansterie.database_backend.util.AppConstants;

@Service
public class DatabaseUserPassUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public DatabaseUserPassUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(AppConstants.USERNAME_NOT_FOUND_MESSAGE + username));
        
        return new DatabaseUserPassUserDetails(user);
    }
}
