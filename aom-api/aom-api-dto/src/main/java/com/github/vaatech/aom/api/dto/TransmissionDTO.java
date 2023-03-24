package com.github.vaatech.aom.api.dto;

import com.github.vaatech.aom.core.model.common.BaseDTO;
import lombok.Data;

@Data
public class TransmissionDTO implements BaseDTO<Long> {
  private Long id;
  private String name;
}
