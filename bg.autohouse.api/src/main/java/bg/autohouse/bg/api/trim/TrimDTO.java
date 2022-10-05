package bg.autohouse.bg.api.trim;

import bg.autohouse.util.common.dto.BaseDTO;
import lombok.Data;

@Data
public class TrimDTO implements BaseDTO<String> {
  private String id;
  private String name;
}
