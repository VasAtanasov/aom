package com.github.vaatech.aom.core.util;

import jakarta.persistence.EntityManager;

public class JpaSupport {
  public static final JpaSupport INSTANCE = new JpaSupport();

  private static EntityManager em;

  public static void setEntityManager(EntityManager entityManager) {
    em = entityManager;
  }
}
