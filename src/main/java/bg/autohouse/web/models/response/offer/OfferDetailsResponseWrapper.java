package bg.autohouse.web.models.response.offer;

import java.util.ArrayList;
import java.util.List;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OfferDetailsResponseWrapper {
  private OfferDetailsResponseModel offer;
  @Builder.Default private List<String> images = new ArrayList<>();
}
