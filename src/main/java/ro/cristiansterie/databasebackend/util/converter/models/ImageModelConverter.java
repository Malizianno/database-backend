package ro.cristiansterie.databasebackend.util.converter.models;

import org.mapstruct.Mapper;
import ro.cristiansterie.databasebackend.dto.ImageDTO;
import ro.cristiansterie.databasebackend.dto.LanguageDTO;
import ro.cristiansterie.databasebackend.model.ImageEntity;
import ro.cristiansterie.databasebackend.model.LanguageEntity;
import ro.cristiansterie.databasebackend.util.converter.ModelConverter;

@Mapper(componentModel = "spring")
public interface ImageModelConverter extends ModelConverter<ImageEntity, ImageDTO> {

}
