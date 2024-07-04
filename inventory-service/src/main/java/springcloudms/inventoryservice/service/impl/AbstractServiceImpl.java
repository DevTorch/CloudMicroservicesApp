package springcloudms.inventoryservice.service.impl;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;
import springcloudms.inventoryservice.service.AbstractService;

import java.util.Optional;

public class AbstractServiceImpl<T, PK> implements AbstractService<T, PK> {

    private final JpaRepository<T, PK> jpaRepository;

    public AbstractServiceImpl(JpaRepository<T, PK> jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public Optional<T> findById(PK id) {
        return jpaRepository.findById(id);
    }

    @Override
    @Transactional
    public T save(T entity) {
        return jpaRepository.save(entity);
    }

    @Override
    @Transactional
    public T update(T entity) {
        return jpaRepository.saveAndFlush(entity);
    }

    @Override
    @Transactional
    public void deleteById(PK id) {
        jpaRepository.deleteById(id);
    }

    @Override
    public boolean existsById(PK id) {
        return jpaRepository.existsById(id);
    }
}
