package ro.cristiansterie.databasebackend.controller;

import ro.cristiansterie.databasebackend.dto.MaterialDTO;
import ro.cristiansterie.databasebackend.service.MaterialService;

class MaterialControllerTest extends AbstractCrudControllerTest {
	@Override
	protected ControllerCase controllerCase() {
		return new ControllerCase("/materials", MaterialController.class, MaterialService.class, MaterialDTO.class,
				"{\"name\":\"Silver\"}");
	}
}
