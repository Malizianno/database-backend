package ro.cristiansterie.databasebackend.controller;

import ro.cristiansterie.databasebackend.dto.LanguageDTO;
import ro.cristiansterie.databasebackend.service.LanguageService;

class LanguageControllerTest extends AbstractCrudControllerTest {
	@Override
	protected ControllerCase controllerCase() {
		return new ControllerCase("/languages", LanguageController.class, LanguageService.class, LanguageDTO.class,
				"{\"name\":\"Romanian\"}");
	}
}
