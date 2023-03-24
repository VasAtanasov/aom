package com.github.vaatech.aom.api.dto;

import com.github.vaatech.aom.core.model.common.BaseDTO;
import lombok.Data;

@Data
public class CarDTO implements BaseDTO<String> {
  private String id;
  private Integer year;
}
