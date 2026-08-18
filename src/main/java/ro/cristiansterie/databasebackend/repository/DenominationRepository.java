package ro.cristiansterie.databasebackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ro.cristiansterie.databasebackend.model.DenominationEntity;

public interface DenominationRepository extends JpaRepository<DenominationEntity, Long> {
}
