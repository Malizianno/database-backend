package ro.cristiansterie.databasebackend.util.converter;

import org.mapstruct.Mapper;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;


public interface ModelConverter<E, D> {

    abstract D toDto(E entity);

    abstract E toEntity(D dto);

    // Convert Collections

    abstract List<D> toDtoList(Collection<E> entities);

    abstract List<E> toEntityList(Collection<D> dtos);

    abstract Set<D> toDtoSet(Collection<E> entities);

    abstract Set<E> toEntitySet(Collection<D> dtos);
}
