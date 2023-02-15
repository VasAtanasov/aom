package bg.autohouse.backend.util.common.persistance;

import jakarta.persistence.EntityManager;

public class JpaSupport {
  public static final JpaSupport INSTANCE = new JpaSupport();

  private static EntityManager em;

  public static void setEntityManager(EntityManager entityManager) {
    em = entityManager;
  }
}
