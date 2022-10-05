package bg.autohouse.spider.domain.dto.cg;

import bg.autohouse.bg.api.engine.EngineDTO;
import bg.autohouse.bg.api.option.OptionDTO;
import bg.autohouse.bg.api.transmission.TransmissionWrapper;
import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

@Data
public class TrimFullDTO implements Serializable {
  private String makerId;
  private String makerName;
  private String modelId;
  private String modelName;
  private String carId;
  private Integer carName;
  private String trimId;
  private String trimName;
  private List<EngineDTO> engines;
  private TransmissionWrapper.InnerTransmissionWrapper transmissions;
  private Map<String, List<OptionDTO>> options;
}
