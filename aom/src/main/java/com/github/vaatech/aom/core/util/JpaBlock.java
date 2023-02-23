package com.github.vaatech.aom.core.util;

import jakarta.persistence.EntityManager;

@FunctionalInterface
public interface JpaBlock<T> {
  T runInJpa(EntityManager entityManager);
}
