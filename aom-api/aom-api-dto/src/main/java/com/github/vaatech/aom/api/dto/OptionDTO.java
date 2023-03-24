package com.github.vaatech.aom.api.dto;

import com.github.vaatech.aom.core.model.common.BaseDTO;
import lombok.Data;

@Data
public class OptionDTO implements BaseDTO<Long> {
  private boolean important;
  private boolean active;
  private String label;
  private Long id;
}
