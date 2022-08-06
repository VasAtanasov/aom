package bg.autohouse.spider.domain.dto.cg;

import java.io.Serializable;
import lombok.Data;

@Data
public class AutoDTO implements Serializable {
  private String makerId;
  private String makerName;
  private String modelId;
  private String modelName;
  private int year;
  private String trimId;
  private String trimName;
  private String engines;
  private String transmissions;
  private String options;

  public static AutoBuilder builder() {
    return new AutoBuilder();
  }

  public static class AutoBuilder {
    private String makerName;
    private String modelName;
    private int year;
    private String trimName;
    private String engines;
    private String transmissions;
    private String options;

    public AutoBuilder makerName(String name) {
      this.makerName = name;
      return this;
    }

    public AutoBuilder modelName(String name) {
      this.modelName = name;
      return this;
    }

    public AutoBuilder year(int year) {
      this.year = year;
      return this;
    }

    public AutoBuilder trimName(String name) {
      this.trimName = name;
      return this;
    }

    public AutoBuilder engines(String engines) {
      this.engines = engines;
      return this;
    }

    public AutoBuilder transmissions(String transmissions) {
      this.transmissions = transmissions;
      return this;
    }

    public AutoBuilder options(String options) {
      this.options = options;
      return this;
    }

    public AutoDTO build() {
      AutoDTO auto = new AutoDTO();
      auto.setMakerName(this.makerName);
      auto.setModelName(this.modelName);
      auto.setYear(this.year);
      auto.setTrimName(this.trimName);
      auto.setEngines(this.engines);
      auto.setTransmissions(this.transmissions);
      auto.setOptions(this.options);
      return auto;
    }
  }
}
