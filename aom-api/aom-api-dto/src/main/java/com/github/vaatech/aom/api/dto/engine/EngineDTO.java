package com.github.vaatech.aom.api.dto.engine;

import com.github.vaatech.aom.api.dto.BaseDTO;
import lombok.Data;

@Data
public class EngineDTO implements BaseDTO<Long> {
  private Long id;
  private String name;
  private boolean isStandard;
}
