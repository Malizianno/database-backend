package ro.cristiansterie.databasebackend.controller;

import ro.cristiansterie.databasebackend.dto.ImageDTO;
import ro.cristiansterie.databasebackend.service.ImageService;

class ImageControllerTest extends AbstractCrudControllerTest {
	@Override
	protected ControllerCase controllerCase() {
		return new ControllerCase("/images", ImageController.class, ImageService.class, ImageDTO.class,
				"{\"imageUrl\":\"https://example.test/image.png\"}");
	}
}
