package bg.autohouse.util.common.persistance;

import jakarta.persistence.EntityManager;

@FunctionalInterface
public interface JpaBlock<T> {
  T runInJpa(EntityManager entityManager);
}
