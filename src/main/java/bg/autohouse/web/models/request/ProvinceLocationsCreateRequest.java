package bg.autohouse.web.models.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor(staticName = "of")
public class ProvinceLocationsCreateRequest {
  private Long id;
  @NonNull private Integer ekatte;
  @NonNull private String name;
  private List<LocationCreateRequest> locations = new ArrayList<>();
}
