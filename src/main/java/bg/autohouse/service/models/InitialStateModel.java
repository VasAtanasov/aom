package bg.autohouse.service.models;

import bg.autohouse.service.models.offer.OfferServiceModel;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
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
@JsonPropertyOrder({"isUserLoggedIn", "totalCount", "topOffers"})
public class InitialStateModel {

  @JsonProperty("isUserLoggedIn")
  @Builder.Default
  private boolean isUserLoggedIn = false;

  @JsonProperty("totalCount")
  @Builder.Default
  private Long totalCount = 0L;

  @Builder.Default private List<String> searchCriteriaNamesForCheckboxCriteria = new ArrayList<>();
  @Builder.Default private List<String> searchCriteriaNamesForSelectCriteria = new ArrayList<>();
  @Builder.Default private List<String> searchCriteriaNamesForRangeCriteria = new ArrayList<>();

  @Builder.Default private List<MakerServiceModel> makers = new ArrayList<>();
  @Builder.Default private List<OfferServiceModel> topOffers = new ArrayList<>();

  @Builder.Default private Map<String, Object> metadata = new LinkedHashMap<>();
}
