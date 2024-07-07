package springcloudms.inventoryservice.model.mapper;

import java.util.List;

public interface Mappable<E, D> {
    E toEntity(D dto);

    List<E> toEntities(List<D> dtos);

    D toDto(E entity);

    List<D> toDto(List<E> entities);
}
