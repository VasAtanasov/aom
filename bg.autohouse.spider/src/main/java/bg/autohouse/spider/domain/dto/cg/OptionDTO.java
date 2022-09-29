package bg.autohouse.spider.domain.dto.cg;

import lombok.Data;

import java.io.Serializable;

@Data
public class OptionDTO implements Serializable {
  private boolean important;
  private boolean active;
  private String label;
  private Integer id;
}
