package ro.cristiansterie.databasebackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ro.cristiansterie.databasebackend.model.DomainEntity;

import java.util.UUID;

public interface DomainRepository extends JpaRepository<DomainEntity, UUID> {
}
