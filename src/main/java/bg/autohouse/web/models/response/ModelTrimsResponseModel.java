package bg.autohouse.web.models.response;

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
@Builder
@AllArgsConstructor
public class ModelTrimsResponseModel {
  private Long id;
  private Long makerId;
  private String name;
  private String makerName;
  @Builder.Default private List<TrimResponseModel> trims = new ArrayList<>();
}
