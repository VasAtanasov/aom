package bg.autohouse.web.models.response;

import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MakerResponseWrapper {
  private Long id;
  private String name;
  private List<ModelResponseModel> models = new ArrayList<>();
}
