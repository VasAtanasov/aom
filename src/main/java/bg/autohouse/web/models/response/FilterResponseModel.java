package bg.autohouse.web.models.response;

import bg.autohouse.data.models.enums.AccountType;
import bg.autohouse.data.models.enums.BodyStyle;
import bg.autohouse.data.models.enums.Color;
import bg.autohouse.data.models.enums.Drive;
import bg.autohouse.data.models.enums.Feature;
import bg.autohouse.data.models.enums.FuelType;
import bg.autohouse.data.models.enums.State;
import bg.autohouse.data.models.enums.Transmission;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class FilterResponseModel {
  private String id;
  private String makerName;
  private String modelName;
  private FuelType fuelType;
  private Transmission transmission;
  private BodyStyle bodyStyle;
  private Color color;
  private Drive drive;
  private Integer priceFrom;
  private Integer priceTo;
  private Integer mileageFrom;
  private Integer mileageTo;
  private Integer doorsFrom;
  private Integer doorsTo;
  private Integer yearFrom;
  private Integer yearTo;
  private List<Feature> features = new ArrayList<>();
  private List<AccountType> seller = new ArrayList<>();
  private List<State> state = new ArrayList<>();
  private Boolean hasAccident;
  private String userId;
}
