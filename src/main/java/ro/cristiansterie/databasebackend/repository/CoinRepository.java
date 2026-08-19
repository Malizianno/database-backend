package ro.cristiansterie.databasebackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ro.cristiansterie.databasebackend.model.CoinEntity;

import java.util.UUID;

public interface CoinRepository extends JpaRepository<CoinEntity, UUID> {
}
