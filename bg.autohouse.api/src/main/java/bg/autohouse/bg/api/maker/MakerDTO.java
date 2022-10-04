package bg.autohouse.bg.api.maker;

import bg.autohouse.util.common.dto.BaseDTO;
import lombok.Data;

@Data
public class MakerDTO implements BaseDTO<String>
{
  private String id;
  private String name;
}
