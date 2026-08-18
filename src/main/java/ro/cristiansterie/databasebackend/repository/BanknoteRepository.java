package ro.cristiansterie.databasebackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ro.cristiansterie.databasebackend.model.BanknoteEntity;

public interface BanknoteRepository extends JpaRepository<BanknoteEntity, Long> {
}
