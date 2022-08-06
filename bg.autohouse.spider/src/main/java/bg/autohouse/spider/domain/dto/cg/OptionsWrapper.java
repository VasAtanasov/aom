package bg.autohouse.spider.domain.dto.cg;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OptionsWrapper implements Serializable {
  private Map<String, List<OptionDTO>> optionsMap;
}
