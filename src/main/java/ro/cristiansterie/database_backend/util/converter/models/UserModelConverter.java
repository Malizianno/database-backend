package ro.cristiansterie.database_backend.util.converter.models;

import org.springframework.stereotype.Service;
import ro.cristiansterie.database_backend.dto.UserDTO;
import ro.cristiansterie.database_backend.model.UserEntity;
import ro.cristiansterie.database_backend.util.converter.AbstractModelConverter;

@Service
public class UserModelConverter extends AbstractModelConverter<UserEntity, UserDTO> {

    protected UserModelConverter() {
        super(UserEntity.class, UserDTO.class);
    }
}
