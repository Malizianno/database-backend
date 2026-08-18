package ro.cristiansterie.databasebackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ro.cristiansterie.databasebackend.model.CountryEntity;

public interface CountryRepository extends JpaRepository<CountryEntity, Long> {
}
