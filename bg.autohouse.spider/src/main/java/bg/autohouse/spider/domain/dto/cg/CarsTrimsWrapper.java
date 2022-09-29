package bg.autohouse.spider.domain.dto.cg;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class CarsTrimsWrapper implements Serializable {
  private List<CarTrimsDTO> cars;
}
