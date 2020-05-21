package bg.autohouse.web.models.request;

import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// TODO refactor
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FilterRequest {
  private String makerName;
  private Long makerId;
  private String modelName;
  private Long modelId;
  private String fuelType;
  private String transmission;
  private String bodyStyle;
  private String color;
  private String drive;
  private Integer priceFrom;
  private Integer priceTo;
  private Integer mileageFrom;
  private Integer mileageTo;
  private Integer doorsFrom;
  private Integer doorsTo;
  private Integer yearFrom;
  private Integer yearTo;
  @Builder.Default private List<String> features = new ArrayList<>();
  @Builder.Default private List<String> seller = new ArrayList<>();
  @Builder.Default private List<String> state = new ArrayList<>();
  private Boolean hasAccident;
}
