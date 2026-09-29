package ro.cristiansterie.databasebackend.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.jpa.repository.JpaRepository;
import ro.cristiansterie.databasebackend.dto.BookDTO;
import ro.cristiansterie.databasebackend.model.BookEntity;
import ro.cristiansterie.databasebackend.repository.BookRepository;
import ro.cristiansterie.databasebackend.util.converter.ModelConverter;
import ro.cristiansterie.databasebackend.util.converter.models.BookModelConverter;

import java.util.Set;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class BookServiceTest extends AbstractCrudServiceTest<BookEntity, BookDTO> {
	@Mock private BookRepository repository;
	@Mock private BookModelConverter converter;
	private BookEntity entity;
	private BookDTO dto;

	@BeforeEach
	void setUp() {
		entity = new BookEntity();
		dto = new BookDTO(ID, UUID.randomUUID(), UUID.randomUUID(), UUID.randomUUID(),
				"History", "A. Author", "Description", "978-1", 240, "https://book.test", 2020, 2019);
	}

	@Override protected JpaRepository<BookEntity, UUID> repository() { return repository; }
	@Override protected ModelConverter<BookEntity, BookDTO> converter() { return converter; }
	@Override protected BookEntity entity() { return entity; }
	@Override protected BookDTO dto() { return dto; }
	@Override protected CrudOperations<BookDTO> service() {
		var service = new BookService(repository, converter);
		return new CrudOperations<>() {
			public BookDTO find(UUID id) { return service.findById(id); }
			public Set<BookDTO> findAll() { return service.findAll(); }
			public BookDTO save(BookDTO value) { return service.save(value); }
			public BookDTO update(UUID id, BookDTO value) { return service.update(id, value); }
			public void delete(UUID id) { service.delete(id); }
		};
	}
	@Override protected void assertUpdated(BookEntity updated) {
		assertThat(updated.getTitle()).isEqualTo(dto.title());
		assertThat(updated.getAuthor()).isEqualTo(dto.author());
		assertThat(updated.getDescription()).isEqualTo(dto.description());
		assertThat(updated.getIsbn()).isEqualTo(dto.isbn());
		assertThat(updated.getPages()).isEqualTo(dto.pages());
		assertThat(updated.getLink()).isEqualTo(dto.link());
		assertThat(updated.getCollectionId()).isEqualTo(dto.collectionId());
		assertThat(updated.getDomainId()).isEqualTo(dto.domainId());
		assertThat(updated.getLanguageId()).isEqualTo(dto.languageId());
		assertThat(updated.getPublishedYear()).isEqualTo(dto.publishedYear());
		assertThat(updated.getPrintedYear()).isEqualTo(dto.printedYear());
	}
}
