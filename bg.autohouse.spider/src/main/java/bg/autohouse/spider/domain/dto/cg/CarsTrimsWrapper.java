package bg.autohouse.spider.domain.dto.cg;


import lombok.Data;

import java.util.List;

@Data
public class CarsTrimsWrapper
{
  private List<CarTrimsDTO> cars;
}
