package com.github.vaatech.aom.feature.vehicle.trim;

import com.github.vaatech.aom.feature.common.entity.BaseDTO;
import lombok.Data;

@Data
public class TrimDTO implements BaseDTO<String> {
  private String id;
  private String name;
}
