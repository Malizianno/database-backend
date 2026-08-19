package ro.cristiansterie.databasebackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ro.cristiansterie.databasebackend.model.LanguageEntity;

import java.util.UUID;

public interface LanguageRepository extends JpaRepository<LanguageEntity, UUID> {
}
