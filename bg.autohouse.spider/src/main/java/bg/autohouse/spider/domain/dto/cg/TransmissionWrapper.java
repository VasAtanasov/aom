package bg.autohouse.spider.domain.dto.cg;

import java.io.Serializable;
import java.util.List;
import lombok.Data;

@Data
public class TransmissionWrapper implements Serializable {
  private InnerTransmissionWrapper all;
  private InnerTransmissionWrapper trimSpecific;

  @Data
  public static class InnerTransmissionWrapper implements Serializable{
    private String label;
    private List<TransmissionDTO> values;
  }
}
