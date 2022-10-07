package bg.autohouse.spider.domain.dto.cg;

import bg.autohouse.api.dto.engine.EngineDTO;
import bg.autohouse.api.dto.option.OptionDTO;
import bg.autohouse.api.dto.transmission.TransmissionWrapper;
import lombok.ToString;

import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Supplier;

@ToString
public class TrimBuilder {
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

  public TrimBuilder() {}

  public TrimBuilder(TrimBuilder builder) {
    this.makerId = builder.makerId;
    this.makerName = builder.makerName;
    this.modelId = builder.modelId;
    this.modelName = builder.modelName;
    this.carId = builder.carId;
    this.carName = builder.carName;
    this.trimName = builder.trimName;
    this.trimId = builder.trimId;
    this.engines = builder.engines;
    this.transmissions = builder.transmissions;
    this.options = builder.options;
  }

  public TrimBuilder(TrimFullDTO trim) {
    this.makerId = trim.getMakerId();
    this.makerName = trim.getMakerName();
    this.modelId = trim.getModelId();
    this.modelName = trim.getModelName();
    this.carId = trim.getCarId();
    this.carName = trim.getCarName();
    this.trimName = trim.getTrimName();
    this.trimId = trim.getTrimId();
    this.engines = trim.getEngines();
    this.transmissions = trim.getTransmissions();
    this.options = trim.getOptions();
  }

  public static TrimBuilder builder() {
    return new TrimBuilder();
  }

  public static <I, N> BiFunction<I, N, TrimBuilder> toBuilder(
      BiConsumer<TrimBuilder, I> idConsumer, BiConsumer<TrimBuilder, N> nameConsumer) {
    return toBuilder(idConsumer, nameConsumer, TrimBuilder::new);
  }

  public static <I, N> BiFunction<I, N, TrimBuilder> toBuilder(
      BiConsumer<TrimBuilder, I> idConsumer,
      BiConsumer<TrimBuilder, N> nameConsumer,
      Supplier<TrimBuilder> builderSupplier) {
    return (id, name) -> {
      TrimBuilder builder = builderSupplier.get();
      idConsumer.accept(builder, id);
      nameConsumer.accept(builder, name);
      return builder;
    };
  }

  public TrimBuilder makerId(String makerId) {
    this.makerId = makerId;
    return this;
  }

  public TrimBuilder makerName(String name) {
    this.makerName = name;
    return this;
  }

  public TrimBuilder modelName(String name) {
    this.modelName = name;
    return this;
  }

  public TrimBuilder modelId(String modelId) {
    this.modelId = modelId;
    return this;
  }

  public TrimBuilder carId(String carId) {
    this.carId = carId;
    return this;
  }

  public TrimBuilder carName(Integer carName) {
    this.carName = carName;
    return this;
  }

  public TrimBuilder trimId(String trimId) {
    this.trimId = trimId;
    return this;
  }

  public TrimBuilder trimName(String name) {
    this.trimName = name;
    return this;
  }

  public TrimBuilder engines(List<EngineDTO> engines) {
    this.engines = engines;
    return this;
  }

  public TrimBuilder transmissions(TransmissionWrapper.InnerTransmissionWrapper transmissions) {
    this.transmissions = transmissions;
    return this;
  }

  public TrimBuilder options(Map<String, List<OptionDTO>> options) {
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

  public String buildTitle() {
    return String.format("%s %s %d %S", makerName, modelName, carName, trimName);
  }
}
