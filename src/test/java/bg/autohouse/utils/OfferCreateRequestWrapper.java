package bg.autohouse.utils;

import bg.autohouse.web.models.request.offer.OfferCreateRequest;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OfferCreateRequestWrapper {
  private OfferCreateRequest offer;
  private List<String> images;
}
