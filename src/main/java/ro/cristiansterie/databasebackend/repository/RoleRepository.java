package ro.cristiansterie.databasebackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ro.cristiansterie.databasebackend.model.RoleEntity;
import ro.cristiansterie.databasebackend.model.UserEntity;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<RoleEntity, Long> {

}
