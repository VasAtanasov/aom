package bg.autohouse.web.models.response;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Singular;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MakerResponseWrapper {
  private Long id;
  private String name;
  @Singular private List<ModelResponseModel> models;
}
