package bg.autohouse.api.dto.car;

import bg.autohouse.api.dto.BaseDTO;
import bg.autohouse.api.dto.trim.TrimDTO;
import lombok.Data;

import java.util.List;

@Data
public class CarTrimsDTO implements BaseDTO<String> {
  private String id;
  private List<TrimDTO> trims;
}
