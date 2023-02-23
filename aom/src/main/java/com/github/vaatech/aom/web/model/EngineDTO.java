package com.github.vaatech.aom.web.model;

import com.github.vaatech.aom.core.model.common.BaseDTO;
import lombok.Data;

@Data
public class EngineDTO implements BaseDTO<Long> {
  private Long id;
  private String name;
  private boolean isStandard;
}
