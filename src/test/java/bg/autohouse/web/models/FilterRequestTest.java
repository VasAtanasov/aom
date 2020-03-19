package bg.autohouse.web.models;

import static org.assertj.core.api.Assertions.assertThat;

import bg.autohouse.SingletonModelMapper;
import bg.autohouse.data.models.Filter;
import bg.autohouse.data.models.enums.FuelType;
import bg.autohouse.util.ModelMapperWrapper;
import bg.autohouse.util.ModelMapperWrapperImpl;
import bg.autohouse.web.models.request.FilterRequest;
import org.junit.jupiter.api.Test;

public class FilterRequestTest {

  private ModelMapperWrapper modelMapper =
      new ModelMapperWrapperImpl(SingletonModelMapper.mapper());

  @Test
  void whenMap_stringFuelType_shouldReturnEnum() {
    FilterRequest filterRequestGasoline =
        FilterRequest.builder().fuelType(FuelType.GASOLINE.name()).build();
    FilterRequest filterRequestHydrogen =
        FilterRequest.builder().fuelType(FuelType.HYDROGEN.name()).build();

    Filter filterGasoline = modelMapper.map(filterRequestGasoline, Filter.class);
    Filter filterHydrogen = modelMapper.map(filterRequestHydrogen, Filter.class);

    assertThat(filterGasoline.getFuelType()).isEqualTo(FuelType.GASOLINE);
    assertThat(filterHydrogen.getFuelType()).isEqualTo(FuelType.HYDROGEN);
  }

  @Test
  void whenMap_priceCriteria_shouldMapCorrect() {
    FilterRequest filterRequestGasoline =
        FilterRequest.builder().priceFrom(100).priceTo(500).build();

    Filter filter = modelMapper.map(filterRequestGasoline, Filter.class);
    assertThat(filter.getPrice().getFrom()).isEqualTo(100);
    assertThat(filter.getPrice().getTo()).isEqualTo(500);
  }
}
