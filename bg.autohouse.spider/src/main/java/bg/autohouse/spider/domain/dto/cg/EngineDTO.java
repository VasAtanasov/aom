package bg.autohouse.spider.domain.dto.cg;

import lombok.Data;

import java.io.Serializable;

@Data
public class EngineDTO implements Serializable {
  private Integer id;
  private String name;
  private boolean isStandard;
}
