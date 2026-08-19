package ro.cristiansterie.databasebackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ro.cristiansterie.databasebackend.model.BookEntity;

import java.util.UUID;

public interface BookRepository extends JpaRepository<BookEntity, UUID> {
}
