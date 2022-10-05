package bg.autohouse.bg.api.model;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
public class ModelsCarsWrapper implements Serializable {
  private List<ModelCarsDTO> models;

  public List<ModelCarsDTO> getModels() {
    return models == null ? new ArrayList<>() : models;
  }
}
