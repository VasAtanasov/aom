package bg.autohouse.spider.domain.dto.cg;

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

  public static TrimFullBuilder builder() {
    return new TrimFullBuilder();
  }

  public static class TrimFullBuilder {
    private String makerId;
    private String makerName;
    private String modelId;
    private String modelName;
    private String carId;
    private Integer carName;
    private String trimName;
    private String trimId;
    private List<EngineDTO> engines;
    private TransmissionWrapper.InnerTransmissionWrapper transmissions;
    private Map<String, List<OptionDTO>> options;

    public TrimFullBuilder makerId(String makerId) {
      this.makerId = makerId;
      return this;
    }

    public TrimFullBuilder makerName(String name) {
      this.makerName = name;
      return this;
    }

    public TrimFullBuilder modelName(String name) {
      this.modelName = name;
      return this;
    }

    public TrimFullBuilder modelId(String modelId) {
      this.modelId = modelId;
      return this;
    }

    public TrimFullBuilder carId(String carId) {
      this.carId = carId;
      return this;
    }

    public TrimFullBuilder carName(Integer carName) {
      this.carName = carName;
      return this;
    }

    public TrimFullBuilder trimId(String trimId) {
      this.trimId = trimId;
      return this;
    }

    public TrimFullBuilder trimName(String name) {
      this.trimName = name;
      return this;
    }

    public TrimFullBuilder engines(List<EngineDTO> engines) {
      this.engines = engines;
      return this;
    }

    public TrimFullBuilder transmissions(
        TransmissionWrapper.InnerTransmissionWrapper transmissions) {
      this.transmissions = transmissions;
      return this;
    }

    public TrimFullBuilder options(Map<String, List<OptionDTO>> options) {
      this.options = options;
      return this;
    }

    public TrimFullDTO build() {
      TrimFullDTO auto = new TrimFullDTO();
      auto.setMakerId(this.makerId);
      auto.setMakerName(this.makerName);
      auto.setModelId(this.modelId);
      auto.setModelName(this.modelName);
      auto.setCarId(this.carId);
      auto.setCarName(this.carName);
      auto.setTrimId(this.trimId);
      auto.setTrimName(this.trimName);
      auto.setEngines(this.engines);
      auto.setTransmissions(this.transmissions);
      auto.setOptions(this.options);
      return auto;
    }
  }
}
