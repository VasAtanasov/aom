package bg.autohouse.web.models.response.offer;

import java.util.ArrayList;
import java.util.List;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OfferDetailsResponseModel {
  private OfferResponseModel offer;
  @Builder.Default private List<String> images = new ArrayList<>();
}
