package springcloudms.inventoryservice.service;

import java.util.Optional;

public interface AbstractService<T, PK> {

    Optional<T> findById(PK id);

    T save(T entity);

    T update(T entity);

    void deleteById(PK id);

    boolean existsById(PK id);
}
