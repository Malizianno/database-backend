package ro.cristiansterie.databasebackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ro.cristiansterie.databasebackend.model.MaterialEntity;

public interface MaterialRepository extends JpaRepository<MaterialEntity, Long> {
}
