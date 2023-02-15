package com.github.vaatech.aom.api.dto.car;

import com.github.vaatech.aom.api.dto.BaseDTO;
import com.github.vaatech.aom.api.dto.trim.TrimDTO;
import lombok.Data;

import java.util.List;

@Data
public class CarTrimsDTO implements BaseDTO<String> {
  private String id;
  private List<TrimDTO> trims;
}
