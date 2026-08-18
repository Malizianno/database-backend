package ro.cristiansterie.databasebackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ro.cristiansterie.databasebackend.model.ImageEntity;

public interface ImageRepository extends JpaRepository<ImageEntity, Long> {
}
