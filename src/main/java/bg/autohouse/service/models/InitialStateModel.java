package bg.autohouse.service.models;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
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
public class InitialStateModel {
  @Builder.Default private List<String> searchCriteriaNamesForCheckboxCriteria = new ArrayList<>();
  @Builder.Default private List<String> searchCriteriaNamesForSelectCriteria = new ArrayList<>();
  @Builder.Default private List<String> searchCriteriaNamesForRangeCriteria = new ArrayList<>();
  @Builder.Default private Map<String, Object> metadata = new LinkedHashMap<>();
}
