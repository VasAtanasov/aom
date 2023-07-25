package com.github.vaatech.aom.core.repository;

import com.github.vaatech.aom.commons.basic.persistence.criteria.CriteriaObject;
import com.github.vaatech.aom.commons.basic.persistence.model.BaseEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import java.io.Serializable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface ApplicationJpaRepository<T extends BaseEntity<ID>, ID extends Serializable>
    extends JpaRepository<T, ID>, JpaSpecificationExecutor<T> {
  EntityManager getEntityManager();

  Class<T> getDomainClass();

  CriteriaObject<T, ID, T> getCriteriaObject();

  <R> CriteriaObject<T, ID, R> getCriteriaObject(Class<R> resultClass);

  Page<ID> findIdsBy(Specification<T> spec, Pageable pageable);

  <R> Page<R> paged(TypedQuery<R> query, Pageable pageable, Specification<T> spec);

  <R> Page<R> paged(TypedQuery<R> query, Pageable pageable);

  <R> void addQueryOrders(
      Pageable pageable, CriteriaQuery<R> query, CriteriaBuilder builder, Root<T> root);
}
