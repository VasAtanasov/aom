package bg.autohouse.spider.domain.dto.cg;

import java.io.Serializable;
import lombok.Data;

@Data
public class OptionDTO implements Serializable {
  private boolean important;
  private boolean active;
  private String label;
  private Integer id;
}
