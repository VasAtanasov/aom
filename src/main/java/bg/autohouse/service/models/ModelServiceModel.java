package bg.autohouse.service.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor(staticName = "of")
public class ModelServiceModel {
  private Long id;
  private String name;
}
