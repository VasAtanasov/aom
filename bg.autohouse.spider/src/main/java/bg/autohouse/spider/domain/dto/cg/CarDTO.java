package bg.autohouse.spider.domain.dto.cg;

import java.io.Serializable;
import lombok.Data;

@Data
public class CarDTO implements Serializable {
  private String id;
  private Integer year;
}
