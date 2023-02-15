package com.github.vaatech.aom.api.dto.model;

import com.github.vaatech.aom.api.dto.BaseDTO;
import com.github.vaatech.aom.api.dto.car.CarDTO;
import com.github.vaatech.aom.api.dto.maker.MakerDTO;
import lombok.Data;

import java.util.List;

@Data
public class ModelDTO implements BaseDTO<String> {
  private String id;
  private String name;
  private MakerDTO maker;
  private List<CarDTO> cars;
}
