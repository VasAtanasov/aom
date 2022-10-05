package bg.autohouse.bg.api.car;

import bg.autohouse.bg.api.trim.TrimDTO;
import bg.autohouse.util.common.dto.BaseDTO;
import lombok.Data;

import java.util.List;

@Data
public class CarTrimsDTO implements BaseDTO<String> {
  private String id;
  private List<TrimDTO> trims;
}
