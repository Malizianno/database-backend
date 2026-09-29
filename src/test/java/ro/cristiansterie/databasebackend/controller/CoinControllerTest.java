package ro.cristiansterie.databasebackend.controller;

import ro.cristiansterie.databasebackend.dto.CoinDTO;
import ro.cristiansterie.databasebackend.service.CoinService;

class CoinControllerTest extends AbstractCrudControllerTest {
	@Override
	protected ControllerCase controllerCase() {
		return new ControllerCase("/coins", CoinController.class, CoinService.class, CoinDTO.class,
				"{\"description\":\"commemorative coin\"}");
	}
}
