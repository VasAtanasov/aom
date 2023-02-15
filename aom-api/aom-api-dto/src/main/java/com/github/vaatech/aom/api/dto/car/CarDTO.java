package com.github.vaatech.aom.api.dto.car;

import com.github.vaatech.aom.api.dto.BaseDTO;
import lombok.Data;

@Data
public class CarDTO implements BaseDTO<String> {
  private String id;
  private Integer year;
}
