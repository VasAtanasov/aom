package bg.autohouse.backend.feature.common.repository;

import bg.autohouse.util.common.persistance.BaseEntity;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;

import javax.persistence.EntityManager;
import java.io.Serializable;

public class ApplicationJpaRepositoryImpl<T extends BaseEntity<ID>, ID extends Serializable>
    extends SimpleJpaRepository<T, ID> implements ApplicationJpaRepository<T, ID> {
  private final EntityManager entityManager;

  public ApplicationJpaRepositoryImpl(
      JpaEntityInformation<T, ?> entityInformation, EntityManager entityManager) {
    super(entityInformation, entityManager);
    this.entityManager = entityManager;
    int a = 5;
  }

  @Override
  public EntityManager getEntityManager() {
    return this.entityManager;
  }
}
