package ro.cristiansterie.databasebackend.controller;

import ro.cristiansterie.databasebackend.dto.DenominationDTO;
import ro.cristiansterie.databasebackend.service.DenominationService;

class DenominationControllerTest extends AbstractCrudControllerTest {
	@Override
	protected ControllerCase controllerCase() {
		return new ControllerCase("/denominations", DenominationController.class, DenominationService.class,
				DenominationDTO.class, "{\"title\":\"Leu\"}");
	}
}
