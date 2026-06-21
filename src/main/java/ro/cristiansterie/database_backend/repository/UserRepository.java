package ro.cristiansterie.database_backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ro.cristiansterie.database_backend.model.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);
}
