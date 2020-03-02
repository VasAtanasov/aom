package bg.autohouse.service.models;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
public class MakerServiceModel {
  private Long id;
  private String name;

  @Builder
  public MakerServiceModel(Long id, String name) {
    this.id = id;
    this.name = name;
  }
}
