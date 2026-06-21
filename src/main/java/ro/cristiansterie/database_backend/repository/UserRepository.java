package ro.cristiansterie.database_backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ro.cristiansterie.database_backend.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
}
