package ro.cristiansterie.databasebackend.controller;

import ro.cristiansterie.databasebackend.dto.DomainDTO;
import ro.cristiansterie.databasebackend.service.DomainService;

class DomainControllerTest extends AbstractCrudControllerTest {
	@Override
	protected ControllerCase controllerCase() {
		return new ControllerCase("/domains", DomainController.class, DomainService.class, DomainDTO.class,
				"{\"name\":\"History\"}");
	}
}
