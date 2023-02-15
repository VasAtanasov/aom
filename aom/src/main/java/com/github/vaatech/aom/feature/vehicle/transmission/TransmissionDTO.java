package com.github.vaatech.aom.feature.vehicle.transmission;

import com.github.vaatech.aom.feature.common.entity.BaseDTO;
import lombok.Data;

@Data
public class TransmissionDTO implements BaseDTO<Long> {
  private Long id;
  private String name;
}
