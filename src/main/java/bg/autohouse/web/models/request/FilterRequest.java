package bg.autohouse.web.models.request;

import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FilterRequest {
  private Long makerId;
  private Long modelId;
  private String fuelType;
  private String transmission;
  private String bodyStyle;
  private String color;
  private String euroStandard;
  private String drive;
  private Integer priceFrom;
  private Integer priceTo;
  private Integer mileageFrom;
  private Integer mileageTo;
  private Integer doorsFrom;
  private Integer doorsTo;
  private Integer seatsFrom;
  private Integer seatsTo;
  private Integer registrationYearFrom;
  private Integer registrationYearTo;
  private Integer horsePowerFrom;
  private Integer horsePowerTo;
  @Builder.Default private List<String> feature = new ArrayList<>();
  @Builder.Default private List<String> seller = new ArrayList<>();
  @Builder.Default private List<String> state = new ArrayList<>();
  @Builder.Default private List<String> upholstery = new ArrayList<>();
  private boolean hasAccident;
}
