package bg.autohouse.bg.api.model;

import bg.autohouse.bg.api.car.CarDTO;
import bg.autohouse.bg.api.maker.MakerDTO;
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
