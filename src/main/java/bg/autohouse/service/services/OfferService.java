package bg.autohouse.service.services;

import bg.autohouse.service.models.OfferTopServiceModel;
import java.util.List;

public interface OfferService {
  List<OfferTopServiceModel> getTopOffers();
}
