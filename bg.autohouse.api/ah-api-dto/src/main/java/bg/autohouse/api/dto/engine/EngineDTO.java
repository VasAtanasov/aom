package bg.autohouse.api.dto.engine;

import bg.autohouse.api.dto.BaseDTO;
import lombok.Data;

@Data
public class EngineDTO implements BaseDTO<Long> {
  private Long id;
  private String name;
  private boolean isStandard;
}
