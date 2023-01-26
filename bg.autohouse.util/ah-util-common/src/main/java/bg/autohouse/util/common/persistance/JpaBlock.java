package bg.autohouse.util.common.persistance;

import javax.persistence.EntityManager;

@FunctionalInterface
public interface JpaBlock<T> {
  T runInJpa(EntityManager entityManager);
}
