package com.github.vaatech.aom.core.repository;

import com.github.vaatech.aom.commons.basic.persistence.criteria.CriteriaObject;
import com.github.vaatech.aom.commons.basic.persistence.model.BaseEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.LockModeType;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import org.hibernate.Session;
import org.hibernate.engine.jdbc.spi.JdbcServices;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.internal.AbstractSharedSessionContract;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.query.QueryUtils;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.JpaEntityInformationSupport;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.data.jpa.support.PageableUtils;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class ApplicationJpaRepositoryImpl<E extends BaseEntity<ID>, ID extends Serializable>
        extends SimpleJpaRepository<E, ID>
        implements ApplicationJpaRepository<E, ID> {

    private final EntityManager entityManager;
    private final JpaEntityInformation<E, ?> entityInformation;

    public ApplicationJpaRepositoryImpl(
            JpaEntityInformation<E, ?> entityInformation, EntityManager entityManager) {
        super(entityInformation, entityManager);
        this.entityManager = entityManager;
        this.entityInformation = entityInformation;
    }

    public ApplicationJpaRepositoryImpl(Class<E> domainClass, EntityManager entityManager) {
        super(domainClass, entityManager);
        this.entityInformation = JpaEntityInformationSupport.getEntityInformation(domainClass, entityManager);
        this.entityManager = entityManager;
    }

    @Override
    public EntityManager getEntityManager() {
        return this.entityManager;
    }

    @Override
    public Class<E> getDomainClass() {
        return super.getDomainClass();
    }

    @Override
    public CriteriaObject<E, ID, E> getCriteriaObject() {
        return getCriteriaObject(getDomainClass());
    }

    @Override
    public <R> CriteriaObject<E, ID, R> getCriteriaObject(Class<R> resultClass) {
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<R> cq = cb.createQuery(resultClass);
        Root<E> root = cq.from(getDomainClass());
        return new CriteriaObject<>(cb, cq, root);
    }


    @Transactional
    public <S extends E> S persist(S entity) {
        entityManager.persist(entity);
        return entity;
    }

    @Transactional
    public <S extends E> S persistAndFlush(S entity) {
        persist(entity);
        entityManager.flush();
        return entity;
    }

    @Transactional
    public <S extends E> List<S> persistAll(Iterable<S> entities) {
        List<S> result = new ArrayList<>();
        for (S entity : entities) {
            result.add(persist(entity));
        }
        return result;
    }

    @Transactional
    public <S extends E> List<S> persistAllAndFlush(Iterable<S> entities) {
        return executeBatch(() -> {
            List<S> result = new ArrayList<>();
            for (S entity : entities) {
                result.add(persist(entity));
            }
            entityManager.flush();
            return result;
        });
    }

    @Transactional
    public <S extends E> S merge(S entity) {
        return entityManager.merge(entity);
    }

    @Transactional
    public <S extends E> S mergeAndFlush(S entity) {
        S result = merge(entity);
        entityManager.flush();
        return result;
    }

    @Transactional
    public <S extends E> List<S> mergeAll(Iterable<S> entities) {
        List<S> result = new ArrayList<>();
        for (S entity : entities) {
            result.add(merge(entity));
        }
        return result;
    }

    @Transactional
    public <S extends E> List<S> mergeAllAndFlush(Iterable<S> entities) {
        return executeBatch(() -> {
            List<S> result = new ArrayList<>();
            for (S entity : entities) {
                result.add(merge(entity));
            }
            entityManager.flush();
            return result;
        });
    }

    @Override
    public E lockById(ID id, LockModeType lockMode) {
        return entityManager.find(entityInformation.getJavaType(), id, lockMode);
    }

    protected Page<?> findIdsBy(Specification<E> spec, Pageable pageable) {
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();

        CriteriaQuery<?> cq = cb.createQuery(entityInformation.getIdType());
        Root<E> root = cq.from(getDomainClass());
        Path<?> pathId = root.get(entityInformation.getIdAttribute());

        cq.multiselect(pathId).distinct(true);

        Predicate predicate = spec.toPredicate(root, cq, cb);

        if (predicate != null) {
            cq.where(predicate);
        }

        addQueryOrders(pageable, cq, cb, root);

        TypedQuery<?> query = getEntityManager().createQuery(cq);

        if (pageable.isPaged()) {
            query.setFirstResult(PageableUtils.getOffsetAsInteger(pageable));
            query.setMaxResults(pageable.getPageSize());
        }

        List<?> result = query.getResultList();

        return PageableExecutionUtils.getPage(
                result,
                pageable,
                () -> executeCountQuery(getCountQuery(spec, getDomainClass())));
    }

    protected <R> void addQueryOrders(Pageable pageable,
                                      CriteriaQuery<R> query,
                                      CriteriaBuilder builder,
                                      Root<E> root) {

        Sort sort = pageable.isPaged() ? pageable.getSort() : Sort.unsorted();
        if (sort.isSorted()) {
            query.orderBy(QueryUtils.toOrders(sort, root, builder));
        }
    }

    private long executeCountQuery(TypedQuery<Long> query) {

        Assert.notNull(query, "TypedQuery must not be null");

        List<Long> totals = query.getResultList();
        long total = 0L;

        for (Long element : totals) {
            total += element == null ? 0 : element;
        }

        return total;
    }

    protected Integer getBatchSize(Session session) {
        SessionFactoryImplementor sessionFactory = session.getSessionFactory().unwrap(SessionFactoryImplementor.class);
        final JdbcServices jdbcServices =
                sessionFactory.getServiceRegistry().getService(JdbcServices.class);
        if (!jdbcServices.getExtractedMetaDataSupport().supportsBatchUpdates()) {
            return Integer.MIN_VALUE;
        }
        return session.unwrap(AbstractSharedSessionContract.class).getConfiguredJdbcBatchSize();
    }

    protected <R> R executeBatch(Supplier<R> callback) {
        Session session = session();
        Integer jdbcBatchSize = getBatchSize(session);
        Integer originalSessionBatchSize = session.getJdbcBatchSize();
        try {
            if (jdbcBatchSize == null) {
                session.setJdbcBatchSize(10);
            }
            return callback.get();
        } finally {
            session.setJdbcBatchSize(originalSessionBatchSize);
        }
    }

    protected Session session() {
        return entityManager.unwrap(Session.class);
    }
}
