package bg.autohouse.spider.domain.dto.cg;

import java.io.Serializable;
import lombok.Data;

@Data
public class ModelDTO implements Serializable {
  private String id;
  private String name;
  private boolean popular;
  private String url;
}
