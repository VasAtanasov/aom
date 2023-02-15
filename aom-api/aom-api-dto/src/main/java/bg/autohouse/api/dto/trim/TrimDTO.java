package bg.autohouse.api.dto.trim;

import bg.autohouse.api.dto.BaseDTO;
import lombok.Data;

@Data
public class TrimDTO implements BaseDTO<String> {
  private String id;
  private String name;
}
