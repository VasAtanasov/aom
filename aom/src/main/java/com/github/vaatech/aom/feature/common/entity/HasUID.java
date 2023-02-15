package com.github.vaatech.aom.feature.common.entity;

import java.util.UUID;

public interface HasUID {
  default UUID getUID() {
    throw new UnsupportedOperationException();
  }

  default void setUID(UUID uid) {
    throw new UnsupportedOperationException();
  }
}
