package ro.cristiansterie.database_backend.util.converter;

import java.util.Collection;
import java.util.List;
import java.util.Set;

public interface ModelConverter<E, D> {

    D toDto(E entity);

    E toEntity(D dto);

    // Convert Collections

    List<D> toDtoList(Collection<E> entities);

    List<E> toEntityList(Collection<D> dtos);

    Set<D> toDtoSet(Collection<E> entities);

    Set<E> toEntitySet(Collection<D> dtos);
}
