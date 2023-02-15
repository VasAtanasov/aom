package bg.autohouse.api.dto.car;

import bg.autohouse.api.dto.BaseDTO;
import lombok.Data;

@Data
public class CarDTO implements BaseDTO<String> {
  private String id;
  private Integer year;
}
