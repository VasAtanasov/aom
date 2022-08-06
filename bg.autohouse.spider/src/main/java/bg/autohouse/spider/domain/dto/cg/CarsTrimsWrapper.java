package bg.autohouse.spider.domain.dto.cg;

import java.util.List;
import lombok.Data;

@Data
public class CarsTrimsWrapper {
  private List<CarTrimsDTO> cars;
}
