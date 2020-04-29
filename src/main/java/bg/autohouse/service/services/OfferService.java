package bg.autohouse.service.services;

import bg.autohouse.service.models.offer.OfferServiceModel;
import bg.autohouse.web.models.request.FilterRequest;
import bg.autohouse.web.models.request.offer.OfferCreateRequest;
import java.util.List;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface OfferService {
  List<OfferServiceModel> getTopOffers();

  Page<OfferServiceModel> searchOffers(FilterRequest filterRequest, Pageable pageable);

  OfferServiceModel createOffer(OfferCreateRequest request, UUID creatorId);
}
