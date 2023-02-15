package com.github.vaatech.aom.util.common.persistance.entity;

import java.util.UUID;

public interface HasVersion {
  void setUID(UUID uid);

  void setVersion(UUID version);
}
