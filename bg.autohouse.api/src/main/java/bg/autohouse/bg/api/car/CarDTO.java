package bg.autohouse.bg.api.car;

import bg.autohouse.util.common.dto.BaseDTO;
import lombok.Data;

@Data
public class CarDTO implements BaseDTO<String> {
  private String id;
  private Integer year;
}
