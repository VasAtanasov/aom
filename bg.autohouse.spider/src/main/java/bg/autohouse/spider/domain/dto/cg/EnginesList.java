package bg.autohouse.spider.domain.dto.cg;

import java.io.Serializable;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EnginesList implements Serializable {
  private List<EngineDTO> engines;
}
