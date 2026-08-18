package ro.cristiansterie.databasebackend.util.converter.models;

import org.mapstruct.Mapper;
import ro.cristiansterie.databasebackend.dto.MaterialDTO;
import ro.cristiansterie.databasebackend.dto.RoleDTO;
import ro.cristiansterie.databasebackend.model.MaterialEntity;
import ro.cristiansterie.databasebackend.util.converter.ModelConverter;

@Mapper(componentModel = "spring")
public interface MaterialModelConverter extends ModelConverter<MaterialEntity, MaterialDTO> {

}
