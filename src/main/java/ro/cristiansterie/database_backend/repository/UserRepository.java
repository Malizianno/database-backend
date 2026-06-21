package ro.cristiansterie.database_backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ro.cristiansterie.database_backend.model.UserEntity;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findByUsername(String username);
}
