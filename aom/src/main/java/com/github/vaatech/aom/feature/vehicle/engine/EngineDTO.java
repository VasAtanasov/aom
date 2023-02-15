package com.github.vaatech.aom.feature.vehicle.engine;

import com.github.vaatech.aom.feature.common.entity.BaseDTO;
import lombok.Data;

@Data
public class EngineDTO implements BaseDTO<Long> {
  private Long id;
  private String name;
  private boolean isStandard;
}
