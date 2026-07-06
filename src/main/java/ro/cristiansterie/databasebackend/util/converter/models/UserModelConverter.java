package ro.cristiansterie.databasebackend.util.converter.models;

import org.springframework.stereotype.Service;
import ro.cristiansterie.databasebackend.dto.UserDTO;
import ro.cristiansterie.databasebackend.model.UserEntity;
import ro.cristiansterie.databasebackend.util.converter.AbstractModelConverter;

@Service
public class UserModelConverter extends AbstractModelConverter<UserEntity, UserDTO> {

	protected UserModelConverter() {
		super(UserEntity.class, UserDTO.class);
	}
}
