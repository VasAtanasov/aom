package com.github.vaatech.aom.core.repository;

import com.github.vaatech.aom.commons.basic.persistence.model.BaseEntity;
import jakarta.persistence.EntityManager;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;

import java.io.Serializable;

public class ApplicationJpaRepositoryImpl<T extends BaseEntity<ID>, ID extends Serializable>
    extends SimpleJpaRepository<T, ID> implements ApplicationJpaRepository<T, ID> {
  private final EntityManager entityManager;

  public ApplicationJpaRepositoryImpl(
      JpaEntityInformation<T, ?> entityInformation, EntityManager entityManager) {
    super(entityInformation, entityManager);
    this.entityManager = entityManager;
  }

  @Override
  public EntityManager getEntityManager() {
    return this.entityManager;
  }
}
