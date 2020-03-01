package bg.autohouse.service.models;

import java.util.ArrayList;
import java.util.List;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
public class MakerServiceModel {
  private Long id;
  private String name;
  private List<ModelServiceModel> models = new ArrayList<>();

  @Builder
  public MakerServiceModel(Long id, String name) {
    this.id = id;
    this.name = name;
  }
}
