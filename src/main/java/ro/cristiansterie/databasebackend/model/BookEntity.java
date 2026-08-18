package ro.cristiansterie.databasebackend.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "books")
@Getter
@Setter
@NoArgsConstructor
public class BookEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private Long collectionId;
	private String title;
	private String author;
	private String description;
	private String isbn;
	private Integer pages;
	private String link;
	private Integer publishedYear;
	private Integer printedYear;
	private Long domainId;
	private Long languageId;
}
