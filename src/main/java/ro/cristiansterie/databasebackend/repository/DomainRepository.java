package ro.cristiansterie.databasebackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ro.cristiansterie.databasebackend.model.DomainEntity;

public interface DomainRepository extends JpaRepository<DomainEntity, Long> {
}
