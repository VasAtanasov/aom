package com.github.vaatech.aom.test;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaDelete;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Component
public class DatabaseCleaner {

  @PersistenceContext private EntityManager em;

  @Transactional(propagation = Propagation.REQUIRES_NEW)
  public void clearManagedEntityTablesFromDatabase() {

    // Disable foreign key checks temporarily to allow clearing tables in arbitrary order.
    em.createNativeQuery("SET FOREIGN_KEY_CHECKS = 0").executeUpdate();

    // Here we want to clear only those tables that are mapped to JPA entities. Because
    // database contains also tables used to store Java enumeration names (to ensure data
    // integrity via foreign key constraints) and possibly some other static data we cannot
    // delete all tables.

    em.getMetamodel()
        .getEntities()
        .forEach(
            entityType -> {
              em.createQuery(criteriaDelete(entityType.getJavaType(), em.getCriteriaBuilder()))
                  .executeUpdate();
            });

    em.createNativeQuery("SET FOREIGN_KEY_CHECKS = 1").executeUpdate();
  }

  private static <T> CriteriaDelete<T> criteriaDelete(
      final Class<T> entityJavaType, final CriteriaBuilder cb) {
    final CriteriaDelete<T> deleteCrit = cb.createCriteriaDelete(entityJavaType);
    deleteCrit.from(entityJavaType);
    return deleteCrit;
  }
}
