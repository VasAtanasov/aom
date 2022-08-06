package bg.autohouse.spider.domain.dto.cg;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import lombok.Data;

@Data
public class ModelsCarsWrapper implements Serializable {
  private List<ModelCarsDTO> models;

  public List<ModelCarsDTO> getModels() {
    return models == null ? new ArrayList<>() : models;
  }
}
