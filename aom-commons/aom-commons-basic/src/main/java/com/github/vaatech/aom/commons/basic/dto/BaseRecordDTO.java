package com.github.vaatech.aom.commons.basic.dto;

import java.io.Serializable;

public interface BaseRecordDTO<T extends Serializable> extends BaseDTO<T> {

  T id();

  @Override
  default T getId() {
    return id();
  }
}
