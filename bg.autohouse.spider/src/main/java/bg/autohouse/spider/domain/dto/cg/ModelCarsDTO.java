package bg.autohouse.spider.domain.dto.cg;

import java.io.Serializable;
import java.util.List;
import lombok.Data;

@Data
public class ModelCarsDTO implements Serializable {
  private String id;
  private List<CarDTO> cars;
}
