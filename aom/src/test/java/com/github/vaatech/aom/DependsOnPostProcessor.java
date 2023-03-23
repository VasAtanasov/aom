package com.github.vaatech.aom;

import org.springframework.boot.autoconfigure.AbstractDependsOnBeanFactoryPostProcessor;

public class DependsOnPostProcessor extends AbstractDependsOnBeanFactoryPostProcessor {
  public DependsOnPostProcessor(Class<?> beanClass, String... dependsOn) {
    super(beanClass, dependsOn);
  }
}
