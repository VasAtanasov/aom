package bg.autohouse.service.services;

import bg.autohouse.data.models.enums.FuelType;
import bg.autohouse.service.models.OfferTopServiceModel;
import java.util.List;

public interface OfferService {
  List<OfferTopServiceModel> getTopOffers();

  List<OfferTopServiceModel> getOffersByFuelType(FuelType fuelType);
}
