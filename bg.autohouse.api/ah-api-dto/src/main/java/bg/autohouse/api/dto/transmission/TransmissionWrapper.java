package bg.autohouse.api.dto.transmission;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class TransmissionWrapper implements Serializable {
  private InnerTransmissionWrapper all;
  private InnerTransmissionWrapper trimSpecific;

  @Data
  public static class InnerTransmissionWrapper implements Serializable {
    private String label;
    private List<TransmissionDTO> values;
  }
}
