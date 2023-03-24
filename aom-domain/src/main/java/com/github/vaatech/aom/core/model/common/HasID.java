package com.github.vaatech.aom.core.model.common;

public interface HasID<T> {
  T getId();

  default void setId(T id) {
    throw new UnsupportedOperationException();
  }
}
