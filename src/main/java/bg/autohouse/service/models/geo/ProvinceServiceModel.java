package bg.autohouse.service.models.geo;

import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProvinceServiceModel {
  private Long id;
  private String name;
  private List<LocationServiceModel> locations = new ArrayList<>();
}
