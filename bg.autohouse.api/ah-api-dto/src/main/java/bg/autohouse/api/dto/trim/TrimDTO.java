package bg.autohouse.api.dto.trim;

import bg.autohouse.util.common.dto.BaseDTO;
import lombok.Data;

@Data
public class TrimDTO implements BaseDTO<String> {
  private String id;
  private String name;
}
