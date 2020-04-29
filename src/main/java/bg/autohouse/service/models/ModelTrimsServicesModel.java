package bg.autohouse.service.models;

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
public class ModelTrimsServicesModel {
  private Long id;
  private Long makerId;
  private String makerName;
  private String name;
  @Builder.Default private List<TrimServiceModel> trims = new ArrayList<>();
}
