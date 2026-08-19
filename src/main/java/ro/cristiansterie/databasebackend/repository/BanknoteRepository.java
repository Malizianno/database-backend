package ro.cristiansterie.databasebackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ro.cristiansterie.databasebackend.model.BanknoteEntity;

import java.util.UUID;

public interface BanknoteRepository extends JpaRepository<BanknoteEntity, UUID> {
}
