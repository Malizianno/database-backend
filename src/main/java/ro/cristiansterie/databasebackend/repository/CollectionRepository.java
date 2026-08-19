package ro.cristiansterie.databasebackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ro.cristiansterie.databasebackend.model.CollectionEntity;

import java.util.UUID;

public interface CollectionRepository extends JpaRepository<CollectionEntity, UUID> {
}
