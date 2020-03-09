package bg.autohouse.service.models;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
@Builder
public class MakerServiceModel {
  private Long id;
  private String name;
}
