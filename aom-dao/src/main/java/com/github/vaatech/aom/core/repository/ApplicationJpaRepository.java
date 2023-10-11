package com.github.vaatech.aom.core.repository;

import com.github.vaatech.aom.commons.basic.persistence.criteria.CriteriaObject;
import com.github.vaatech.aom.commons.basic.persistence.model.BaseEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.LockModeType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.Repository;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

@NoRepositoryBean
public interface ApplicationJpaRepository<T extends BaseEntity<ID>, ID extends Serializable>
        extends Repository<T, ID> {
    EntityManager getEntityManager();

    Class<T> getDomainClass();

    CriteriaObject<T, ID, T> getCriteriaObject();

    <R> CriteriaObject<T, ID, R> getCriteriaObject(Class<R> resultClass);

    Page<T> findAll(Specification<T> spec, Pageable pageable);

    Page<T> findAll( Pageable pageable);

    List<T> findAllById(Iterable<ID> ids);

    Optional<T> findById(ID id);

    Optional<T> findOne(Specification<T> spec);

    T getReferenceById(ID id);

    boolean existsById(ID id);

    boolean exists(Specification<T> spec);

    long count();

    long count(Specification<T> spec);


    long delete(Specification<T> spec);

    void delete(T entity);

    void deleteAllInBatch(Iterable<T> entities);

    void deleteById(ID id);

    void deleteAllByIdInBatch(Iterable<ID> ids);

    void flush();

    <S extends T> S persist(S entity);

    <S extends T> S persistAndFlush(S entity);

    <S extends T> List<S> persistAll(Iterable<S> entities);

    <S extends T> List<S> persistAllAndFlush(Iterable<S> entities);

    <S extends T> S merge(S entity);

    <S extends T> S mergeAndFlush(S entity);

    <S extends T> List<S> mergeAll(Iterable<S> entities);

    <S extends T> List<S> mergeAllAndFlush(Iterable<S> entities);

    T lockById(ID id, LockModeType lockMode);
}
