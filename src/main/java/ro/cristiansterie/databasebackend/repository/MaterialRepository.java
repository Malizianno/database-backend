package ro.cristiansterie.databasebackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ro.cristiansterie.databasebackend.model.MaterialEntity;

import java.util.UUID;

public interface MaterialRepository extends JpaRepository<MaterialEntity, UUID> {
}
