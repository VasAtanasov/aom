package com.github.vaatech.aom.api.dto.model;

import com.github.vaatech.aom.api.dto.BaseDTO;
import com.github.vaatech.aom.api.dto.car.CarDTO;
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
