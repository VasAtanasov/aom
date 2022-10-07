package bg.autohouse.api.dto.model;

import bg.autohouse.api.dto.car.CarDTO;
import bg.autohouse.api.dto.maker.MakerDTO;
import bg.autohouse.util.common.dto.BaseDTO;
import lombok.Data;

import java.util.List;

@Data
public class ModelDTO implements BaseDTO<String> {
  private String id;
  private String name;
  private MakerDTO maker;
  private List<CarDTO> cars;
}
