package ro.cristiansterie.database_backend.util.converter;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public abstract class AbstractModelConverter<E, D> implements ModelConverter<E, D> {

    private final ModelMapper modelMapper = new ModelMapper();
    private final Class<E> entityClass;
    private final Class<D> dtoClass;

    protected AbstractModelConverter(Class<E> entityClass, Class<D> dtoClass) {
        this.entityClass = entityClass;
        this.dtoClass = dtoClass;
    }

    @Override
    public D toDto(E entity) {
        return entity == null ? null : modelMapper.map(entity, dtoClass);
    }

    @Override
    public E toEntity(D dto) {
        return dto == null ? null : modelMapper.map(dto, entityClass);
    }

    @Override
    public List<D> toDtoList(Collection<E> entities) {
        return entities == null ? List.of() : entities.stream().map(this::toDto).collect(Collectors.toList());
    }

    @Override
    public List<E> toEntityList(Collection<D> dtos) {
        return dtos == null ? List.of() : dtos.stream().map(this::toEntity).collect(Collectors.toList());
    }

    @Override
    public Set<D> toDtoSet(Collection<E> entities) {
        return entities == null ? Set.of() : entities.stream().map(this::toDto).collect(Collectors.toSet());
    }

    @Override
    public Set<E> toEntitySet(Collection<D> dtos) {
        return dtos == null ? Set.of() : dtos.stream().map(this::toEntity).collect(Collectors.toSet());
    }
}
