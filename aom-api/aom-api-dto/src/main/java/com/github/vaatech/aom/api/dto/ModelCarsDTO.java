package com.github.vaatech.aom.api.dto;

import com.github.vaatech.aom.core.model.common.BaseDTO;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ModelCarsDTO implements BaseDTO<String> {
  private String id;
  private String modelName;
  private String makerId;
  private String makerName;
  private List<CarDTO> cars;
}
