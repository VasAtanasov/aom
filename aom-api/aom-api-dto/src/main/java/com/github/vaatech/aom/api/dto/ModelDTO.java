package com.github.vaatech.aom.api.dto;

import com.github.vaatech.aom.core.model.common.BaseDTO;
import lombok.Data;

import java.util.List;

@Data
public class ModelDTO implements BaseDTO<String> {
  private String id;
  private String name;
  private MakerDTO maker;
  private List<CarDTO> cars;
}
