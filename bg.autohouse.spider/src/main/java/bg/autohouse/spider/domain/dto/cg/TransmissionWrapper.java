package bg.autohouse.spider.domain.dto.cg;

import java.util.List;
import lombok.Data;

@Data
public class TransmissionWrapper {
  private InnerWrapper all;
  private InnerWrapper trimSpecific;

  @Data
  public static class InnerWrapper {
    private String label;
    private List<TransmissionDTO> values;
  }
}
