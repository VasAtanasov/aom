package bg.autohouse.bg.api.maker;

import bg.autohouse.bg.api.model.ModelDTO;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class MakerDTO implements Serializable, Comparable<MakerDTO> {
  private String id;
  private String name;
  private boolean popular;
  private List<ModelDTO> models;

  @Override
  public int compareTo(MakerDTO o) {
    int id = Integer.parseInt(getId().substring(1));
    int otherId = Integer.parseInt(o.getId().substring(1));
    return Integer.compare(id, otherId);
  }
}
