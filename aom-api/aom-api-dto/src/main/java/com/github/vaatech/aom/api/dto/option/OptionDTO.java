package com.github.vaatech.aom.api.dto.option;

import com.github.vaatech.aom.api.dto.BaseDTO;
import lombok.Data;

@Data
public class OptionDTO implements BaseDTO<Long> {
  private boolean important;
  private boolean active;
  private String label;
  private Long id;
}
