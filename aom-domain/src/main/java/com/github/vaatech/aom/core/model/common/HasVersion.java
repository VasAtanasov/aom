package com.github.vaatech.aom.core.model.common;

import java.util.UUID;

public interface HasVersion {
  void setUID(UUID uid);

  void setVersion(UUID version);
}
