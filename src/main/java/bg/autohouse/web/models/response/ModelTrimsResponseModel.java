package bg.autohouse.web.models.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ModelTrimsResponseModel {
  private Long id;
  private Long makerId;
  private String name;
  private String makerName;
  private List<TrimResponseModel> trims = new ArrayList<>();
}
