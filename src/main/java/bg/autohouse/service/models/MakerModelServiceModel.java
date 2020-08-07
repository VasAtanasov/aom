package bg.autohouse.service.models;

import java.util.ArrayList;
import java.util.List;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
public class MakerModelServiceModel {
  private Long id;
  private String name;
  private List<ModelServiceModel> models = new ArrayList<>();
}
