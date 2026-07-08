package ro.cristiansterie.databasebackend.util.converter.models;

import org.mapstruct.Mapper;
import ro.cristiansterie.databasebackend.dto.UserDTO;
import ro.cristiansterie.databasebackend.model.UserEntity;
import ro.cristiansterie.databasebackend.util.converter.ModelConverter;

@Mapper(componentModel = "spring")
public interface UserModelConverter extends ModelConverter<UserEntity, UserDTO> {

}
