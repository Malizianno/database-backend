package ro.cristiansterie.databasebackend.controller;

import ro.cristiansterie.databasebackend.dto.BookDTO;
import ro.cristiansterie.databasebackend.service.BookService;

class BookControllerTest extends AbstractCrudControllerTest {
	@Override
	protected ControllerCase controllerCase() {
		return new ControllerCase("/books", BookController.class, BookService.class, BookDTO.class,
				"{\"title\":\"Test book\"}");
	}
}
