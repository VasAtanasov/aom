package bg.autohouse.web.models.request;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class ProvinceLocationsCreateRequest {
  @NonNull private String name;
  @NonNull private Integer ekatte;
  private List<LocationCreateRequest> locations = new ArrayList<>();
}
