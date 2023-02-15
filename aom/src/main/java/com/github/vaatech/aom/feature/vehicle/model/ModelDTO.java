package com.github.vaatech.aom.feature.vehicle.model;

import com.github.vaatech.aom.feature.common.entity.BaseDTO;
import com.github.vaatech.aom.feature.vehicle.car.CarDTO;
import com.github.vaatech.aom.feature.vehicle.maker.MakerDTO;
import lombok.Data;

import java.util.List;

@Data
public class ModelDTO implements BaseDTO<String> {
  private String id;
  private String name;
  private MakerDTO maker;
  private List<CarDTO> cars;
}
