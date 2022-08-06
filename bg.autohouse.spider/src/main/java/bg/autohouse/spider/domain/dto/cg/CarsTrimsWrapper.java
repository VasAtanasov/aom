package bg.autohouse.spider.domain.dto.cg;

import java.io.Serializable;
import java.util.List;
import lombok.Data;

@Data
public class CarsTrimsWrapper implements Serializable {
  private List<CarTrimsDTO> cars;
}
