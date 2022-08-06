package bg.autohouse.spider.domain.dto.cg;

import java.io.Serializable;
import java.util.List;
import lombok.Data;

@Data
public class MakerDTO implements Serializable {
  private String id;
  private String name;
  private boolean popular;
  private List<ModelDTO> models;
}
