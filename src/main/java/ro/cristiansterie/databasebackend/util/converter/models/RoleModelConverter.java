package ro.cristiansterie.databasebackend.util.converter.models;

import org.mapstruct.Mapper;
import ro.cristiansterie.databasebackend.dto.RoleDTO;
import ro.cristiansterie.databasebackend.model.RoleEntity;
import ro.cristiansterie.databasebackend.util.converter.ModelConverter;

@Mapper(componentModel = "spring")
public interface RoleModelConverter extends ModelConverter<RoleEntity, RoleDTO> {

}
