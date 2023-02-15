package com.github.vaatech.aom.feature.vehicle.car;

import com.github.vaatech.aom.feature.common.entity.BaseDTO;
import lombok.Data;

@Data
public class CarDTO implements BaseDTO<String> {
  private String id;
  private Integer year;
}
