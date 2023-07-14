package com.github.vaatech.aom.core.repository;

import jakarta.persistence.EntityManager;
import java.io.Serializable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface ApplicationJpaRepository<T, ID extends Serializable>
    extends JpaRepository<T, ID>, JpaSpecificationExecutor<T> {
  EntityManager getEntityManager();

  Class<T> getDomainClass();
}
