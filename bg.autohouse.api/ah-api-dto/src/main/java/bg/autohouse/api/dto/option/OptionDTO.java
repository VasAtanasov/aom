package bg.autohouse.api.dto.option;

import bg.autohouse.util.common.dto.BaseDTO;
import lombok.Data;

@Data
public class OptionDTO implements BaseDTO<Long> {
  private boolean important;
  private boolean active;
  private String label;
  private Long id;
}
