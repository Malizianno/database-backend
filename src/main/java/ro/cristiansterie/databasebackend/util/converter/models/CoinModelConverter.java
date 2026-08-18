package ro.cristiansterie.databasebackend.util.converter.models;

import org.mapstruct.Mapper;
import ro.cristiansterie.databasebackend.dto.CoinDTO;
import ro.cristiansterie.databasebackend.model.CoinEntity;
import ro.cristiansterie.databasebackend.util.converter.ModelConverter;

@Mapper(componentModel = "spring")
public interface CoinModelConverter extends ModelConverter<CoinEntity, CoinDTO> {

}
