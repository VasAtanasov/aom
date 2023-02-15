package com.github.vaatech.aom.feature.common.entity;

/**
 * interface which holds actions which should be persisted/merged before entity is saved. used for
 * cases when usual JPA listeners / methods are not suitable (e.g. when we don't want to depend on
 * DB and generate data in the code before entity is merged / persisted).
 */
public interface PreProcessed extends HasVersion
{
  void preCreate();

  void preUpdate();
}
