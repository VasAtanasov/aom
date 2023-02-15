package com.github.vaatech.aom.api.dto;

import java.util.UUID;

public interface HasUID {
  default UUID getUID() {
    throw new UnsupportedOperationException();
  }

  default void setUID(UUID uid) {
    throw new UnsupportedOperationException();
  }
}
