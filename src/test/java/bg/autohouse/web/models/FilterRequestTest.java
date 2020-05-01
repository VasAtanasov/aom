package bg.autohouse.web.models;

import static org.assertj.core.api.Assertions.assertThat;

import bg.autohouse.SingletonModelMapper;
import bg.autohouse.data.models.Filter;
import bg.autohouse.data.models.enums.Feature;
import bg.autohouse.data.models.enums.FuelType;
import bg.autohouse.util.ModelMapperWrapper;
import bg.autohouse.util.ModelMapperWrapperImpl;
import bg.autohouse.web.models.request.FilterRequest;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;

public class FilterRequestTest {

  private ModelMapperWrapper modelMapper =
      new ModelMapperWrapperImpl(SingletonModelMapper.mapper());

  @Test
  void whenMap_stringFuelType_shouldReturnEnum() {
    FilterRequest filterRequestGasoline =
        FilterRequest.builder().fuelType(FuelType.GASOLINE.name()).build();
    FilterRequest filterRequestHydrogen =
        FilterRequest.builder().fuelType(FuelType.HYBRID.name()).build();

    Filter filterGasoline = modelMapper.map(filterRequestGasoline, Filter.class);
    Filter filterHydrogen = modelMapper.map(filterRequestHydrogen, Filter.class);

    assertThat(filterGasoline.getFuelType()).isEqualTo(FuelType.GASOLINE);
    assertThat(filterHydrogen.getFuelType()).isEqualTo(FuelType.HYBRID);
  }

  @Test
  void whenMap_priceCriteria_shouldMapCorrect() {
    FilterRequest filterRequestGasoline =
        FilterRequest.builder().priceFrom(100).priceTo(500).build();

    Filter filter = modelMapper.map(filterRequestGasoline, Filter.class);
    assertThat(filter.getPrice().getFrom()).isEqualTo(100);
    assertThat(filter.getPrice().getTo()).isEqualTo(500);
  }

  @Test
  void whenMap_featureList_shouldMapCorrect() {
    List<String> feature =
        Arrays.asList(
            Feature.AIR_CONDITIONING.name(), Feature.DIGITAL_RADIO.name(), Feature.METALLIC.name());

    FilterRequest filterRequestGasoline = FilterRequest.builder().features(feature).build();

    Filter filter = modelMapper.map(filterRequestGasoline, Filter.class);

    assertThat(filter.getFeatures()).allMatch(f -> f != null);
  }
}
