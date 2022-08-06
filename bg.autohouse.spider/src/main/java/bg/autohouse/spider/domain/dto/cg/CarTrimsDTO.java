package bg.autohouse.spider.domain.dto.cg;

import java.util.List;
import lombok.Data;

@Data
public class CarTrimsDTO {
  private String id;
  private List<TrimDTO> trims;
}
