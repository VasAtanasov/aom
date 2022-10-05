package bg.autohouse.bg.api.transmission;

import bg.autohouse.util.common.dto.BaseDTO;
import lombok.Data;

@Data
public class TransmissionDTO implements BaseDTO<Long> {
  private Long id;
  private String name;
}
