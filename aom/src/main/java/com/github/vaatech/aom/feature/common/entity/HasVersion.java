package com.github.vaatech.aom.feature.common.entity;

import java.util.UUID;

public interface HasVersion {
  void setUID(UUID uid);

  void setVersion(UUID version);
}
