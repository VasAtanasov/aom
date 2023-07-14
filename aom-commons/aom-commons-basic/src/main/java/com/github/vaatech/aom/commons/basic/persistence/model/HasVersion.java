package com.github.vaatech.aom.commons.basic.persistence.model;

import java.util.UUID;

public interface HasVersion {
  void setUID(UUID uid);

  void setVersion(UUID version);
}
