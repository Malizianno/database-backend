package ro.cristiansterie.databasebackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ro.cristiansterie.databasebackend.model.CoinEntity;

public interface CoinRepository extends JpaRepository<CoinEntity, Long> {
}
