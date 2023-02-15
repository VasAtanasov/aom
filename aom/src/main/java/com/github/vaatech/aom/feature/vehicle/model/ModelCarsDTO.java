package com.github.vaatech.aom.feature.vehicle.model;

import com.github.vaatech.aom.feature.common.entity.BaseDTO;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.github.vaatech.aom.feature.vehicle.car.CarDTO;
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
