package bg.autohouse.spider.domain.dto.cg;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class FuelTankCapacityDTO {
  private BigDecimal value;
  private String unit;
}
