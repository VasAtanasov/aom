package bg.autohouse.spider.domain.dto.cg;

import java.io.Serializable;
import java.util.List;
import lombok.Data;

@Data
public class TransmissionWrapper implements Serializable {
  private InnerWrapper all;
  private InnerWrapper trimSpecific;

  @Data
  public static class InnerWrapper implements Serializable{
    private String label;
    private List<TransmissionDTO> values;
  }
}
