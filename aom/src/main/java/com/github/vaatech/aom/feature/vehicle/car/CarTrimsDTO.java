package com.github.vaatech.aom.feature.vehicle.car;

import com.github.vaatech.aom.feature.common.entity.BaseDTO;
import com.github.vaatech.aom.feature.vehicle.trim.TrimDTO;
import lombok.Data;

import java.util.List;

@Data
public class CarTrimsDTO implements BaseDTO<String> {
  private String id;
  private List<TrimDTO> trims;
}
