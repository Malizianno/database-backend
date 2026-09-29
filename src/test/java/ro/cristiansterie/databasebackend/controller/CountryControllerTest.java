package ro.cristiansterie.databasebackend.controller;

import ro.cristiansterie.databasebackend.dto.CountryDTO;
import ro.cristiansterie.databasebackend.service.CountryService;

class CountryControllerTest extends AbstractCrudControllerTest {
	@Override
	protected ControllerCase controllerCase() {
		return new ControllerCase("/countries", CountryController.class, CountryService.class, CountryDTO.class,
				"{\"name\":\"Romania\"}");
	}
}
