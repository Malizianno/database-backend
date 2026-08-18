package ro.cristiansterie.databasebackend.util.converter.models;

import org.mapstruct.Mapper;
import ro.cristiansterie.databasebackend.dto.BookDTO;
import ro.cristiansterie.databasebackend.model.BookEntity;
import ro.cristiansterie.databasebackend.util.converter.ModelConverter;

@Mapper(componentModel = "spring")
public interface BookModelConverter extends ModelConverter<BookEntity, BookDTO> {

}
