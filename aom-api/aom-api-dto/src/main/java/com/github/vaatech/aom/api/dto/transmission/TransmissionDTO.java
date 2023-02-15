package com.github.vaatech.aom.api.dto.transmission;

import com.github.vaatech.aom.api.dto.BaseDTO;
import lombok.Data;

@Data
public class TransmissionDTO implements BaseDTO<Long> {
  private Long id;
  private String name;
}
