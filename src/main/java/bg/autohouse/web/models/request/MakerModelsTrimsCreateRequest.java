package bg.autohouse.web.models.request;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class MakerModelsTrimsCreateRequest {
  private String name;
  private List<ModelTrimsCreateRequest> models = new ArrayList<>();
}
