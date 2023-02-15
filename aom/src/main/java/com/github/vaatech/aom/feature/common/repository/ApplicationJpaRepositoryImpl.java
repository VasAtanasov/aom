package com.github.vaatech.aom.feature.common.repository;

import com.github.vaatech.aom.util.common.persistance.entity.BaseEntity;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;

import jakarta.persistence.EntityManager;
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
