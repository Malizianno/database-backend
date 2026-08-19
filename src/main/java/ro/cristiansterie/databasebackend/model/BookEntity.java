package ro.cristiansterie.databasebackend.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

@Entity
@Table(name = "books")
@Getter
@Setter
@NoArgsConstructor
public class BookEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@UuidGenerator
	private UUID id;
	private UUID collectionId;
	private UUID domainId;
	private UUID languageId;

	private String title;
	private String author;
	private String description;
	private String isbn;
	private Integer pages;
	private String link;
	private Integer publishedYear;
	private Integer printedYear;
}
