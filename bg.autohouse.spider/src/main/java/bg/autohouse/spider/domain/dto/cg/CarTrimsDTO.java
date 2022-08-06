package bg.autohouse.spider.domain.dto.cg;

import java.io.Serializable;
import java.util.List;
import lombok.Data;

@Data
public class CarTrimsDTO implements Serializable {
  private String id;
  private List<TrimDTO> trims;
}
