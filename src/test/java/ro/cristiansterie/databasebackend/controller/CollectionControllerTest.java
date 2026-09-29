package ro.cristiansterie.databasebackend.controller;

import ro.cristiansterie.databasebackend.dto.CollectionDTO;
import ro.cristiansterie.databasebackend.service.CollectionService;

class CollectionControllerTest extends AbstractCrudControllerTest {
	@Override
	protected ControllerCase controllerCase() {
		return new ControllerCase("/collections", CollectionController.class, CollectionService.class, CollectionDTO.class,
				"{\"name\":\"My collection\"}");
	}
}
