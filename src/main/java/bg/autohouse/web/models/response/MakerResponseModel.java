package bg.autohouse.web.models.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
public class MakerResponseModel {

  private Long id;
  private String name;

  @Builder
  public MakerResponseModel(Long id, String name) {
    this.id = id;
    this.name = name;
  }
}
