package bg.autohouse.service.models;

import java.util.ArrayList;
import java.util.List;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MakerModelServiceModel {
  private Long id;
  private String name;
  @Builder.Default private List<ModelServiceModel> models = new ArrayList<>();
}
