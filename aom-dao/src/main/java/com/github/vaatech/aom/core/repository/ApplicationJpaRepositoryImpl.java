package com.github.vaatech.aom.core.repository;

import com.github.vaatech.aom.commons.basic.persistence.criteria.CriteriaObject;
import com.github.vaatech.aom.commons.basic.persistence.model.BaseEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Tuple;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import java.io.Serializable;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.query.QueryUtils;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.data.jpa.support.PageableUtils;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.util.Assert;

public class ApplicationJpaRepositoryImpl<E extends BaseEntity<ID>, ID extends Serializable>
    extends SimpleJpaRepository<E, ID> implements ApplicationJpaRepository<E, ID> {
  private final EntityManager entityManager;
  private final JpaEntityInformation<E, ?> entityInformation;

  public ApplicationJpaRepositoryImpl(
      JpaEntityInformation<E, ?> entityInformation, EntityManager entityManager) {
    super(entityInformation, entityManager);
    this.entityManager = entityManager;
    this.entityInformation = entityInformation;
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

  @Override
  public Page<ID> findIdsBy(Specification<E> spec, Pageable pageable) {
    CriteriaObject<E, ID, Tuple> co = getCriteriaObject(Tuple.class);
    CriteriaBuilder cb = co.cb();
    CriteriaQuery<Tuple> cq = co.cq();
    Root<E> root = co.root();
    Path<ID> pathId = root.get(entityInformation.getRequiredIdAttribute().getName());

    cq.multiselect(pathId).distinct(true);

    Predicate predicate = spec.toPredicate(root, cq, cb);
    if (predicate != null) {
      cq.where(predicate);
    }

    addQueryOrders(pageable, cq, cb, root);

    TypedQuery<Tuple> typedQuery = getEntityManager().createQuery(cq);

    Page<Tuple> result;
    if (pageable.isPaged()) {
      result = paged(typedQuery, pageable, spec);
    } else {
      result = new PageImpl<>(typedQuery.getResultList());
    }

    return result.map(tuple -> tuple.get(pathId));
  }

  @Override
  public <R> Page<R> paged(TypedQuery<R> query, Pageable pageable, Specification<E> spec) {

    if (pageable.isPaged()) {
      query.setFirstResult(PageableUtils.getOffsetAsInteger(pageable));
      query.setMaxResults(pageable.getPageSize());
    }

    return PageableExecutionUtils.getPage(
        query.getResultList(),
        pageable,
        () -> executeCountQuery(getCountQuery(spec, getDomainClass())));
  }

  @Override
  public <R> Page<R> paged(TypedQuery<R> query, Pageable pageable) {
    return paged(query, pageable, null);
  }

  @Override
  public <R> void addQueryOrders(
      Pageable pageable, CriteriaQuery<R> query, CriteriaBuilder builder, Root<E> root) {
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
}
