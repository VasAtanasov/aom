package bg.autohouse.service.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ModelTrimsServicesModel {
  private Long id;
  private Long makerId;
  private String makerName;
  private String name;
  private List<TrimServiceModel> trims = new ArrayList<>();
}
