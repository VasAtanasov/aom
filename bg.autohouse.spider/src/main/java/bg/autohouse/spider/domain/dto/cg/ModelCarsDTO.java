package bg.autohouse.spider.domain.dto.cg;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ModelCarsDTO implements Serializable {
  private String id;
  private String modelName;
  private String makerId;
  private String makerName;
  private List<CarDTO> cars;
}
