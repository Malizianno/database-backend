package ro.cristiansterie.databasebackend.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import ro.cristiansterie.databasebackend.model.BookEntity;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class BookRepositoryTest extends AbstractCrudRepositoryTest<BookEntity> {
	@Autowired
	private BookRepository bookRepository;

	@Override
	protected JpaRepository<BookEntity, UUID> repository() {
		return bookRepository;
	}

	@Override
	protected BookEntity newEntity() {
		BookEntity book = new BookEntity();
		book.setTitle("Repository test " + UUID.randomUUID());
		book.setAuthor("Test author");
		return book;
	}

	@Override
	protected BookEntity updateEntity(BookEntity book) {
		book.setTitle("Updated title");
		return book;
	}

	@Override
	protected UUID entityId(BookEntity book) {
		return book.getId();
	}

	@Override
	protected void assertUpdated(BookEntity book) {
		assertThat(book.getTitle()).isEqualTo("Updated title");
	}
}
