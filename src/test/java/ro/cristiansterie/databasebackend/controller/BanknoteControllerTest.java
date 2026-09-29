package ro.cristiansterie.databasebackend.controller;

import ro.cristiansterie.databasebackend.dto.BanknoteDTO;
import ro.cristiansterie.databasebackend.service.BanknoteService;

class BanknoteControllerTest extends AbstractCrudControllerTest {
	@Override
	protected ControllerCase controllerCase() {
		return new ControllerCase("/banknotes", BanknoteController.class, BanknoteService.class, BanknoteDTO.class,
				"{\"description\":\"commemorative note\"}");
	}
}
