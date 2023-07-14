package com.github.vaatech.aom.core.repository;

import com.github.vaatech.aom.commons.basic.persistence.criteria.CriteriaObject;
import com.github.vaatech.aom.commons.basic.persistence.model.BaseEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import java.io.Serializable;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;

public class ApplicationJpaRepositoryImpl<E extends BaseEntity<ID>, ID extends Serializable>
    extends SimpleJpaRepository<E, ID> implements ApplicationJpaRepository<E, ID> {
  private final EntityManager entityManager;

  public ApplicationJpaRepositoryImpl(
      JpaEntityInformation<E, ?> entityInformation, EntityManager entityManager) {
    super(entityInformation, entityManager);
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

  public CriteriaObject<E, ID, E> getCriteriaObject() {
    return getCriteriaObject(getDomainClass());
  }

  public <R> CriteriaObject<E, ID, R> getCriteriaObject(Class<R> resultClass) {
    CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
    CriteriaQuery<R> cq = cb.createQuery(resultClass);
    Root<E> root = cq.from(getDomainClass());
    return new CriteriaObject<>(cb, cq, root);
  }
}
