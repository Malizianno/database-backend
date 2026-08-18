package ro.cristiansterie.databasebackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ro.cristiansterie.databasebackend.model.CollectionEntity;

public interface CollectionRepository extends JpaRepository<CollectionEntity, Long> {
}
