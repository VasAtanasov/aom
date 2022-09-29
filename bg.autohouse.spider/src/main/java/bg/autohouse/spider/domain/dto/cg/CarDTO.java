package bg.autohouse.spider.domain.dto.cg;

import lombok.Data;

import java.io.Serializable;

@Data
public class CarDTO implements Serializable {
  private String id;
  private Integer year;
}
