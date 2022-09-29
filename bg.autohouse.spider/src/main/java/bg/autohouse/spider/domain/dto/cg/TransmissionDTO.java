package bg.autohouse.spider.domain.dto.cg;

import lombok.Data;

import java.io.Serializable;

@Data
public class TransmissionDTO implements Serializable {
  private Integer id;
  private String name;
}
