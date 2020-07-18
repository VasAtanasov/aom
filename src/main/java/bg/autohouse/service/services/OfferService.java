package bg.autohouse.service.services;

import bg.autohouse.service.models.offer.OfferServiceModel;
import bg.autohouse.web.models.request.FilterRequest;
import bg.autohouse.web.models.request.offer.OfferCreateRequest;
import java.io.IOException;
import java.util.List;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface OfferService {
  List<OfferServiceModel> getLatestOffers();

  OfferServiceModel getOfferById(UUID id);

  List<String> fetchOfferImages(UUID offerId);

  Page<OfferServiceModel> searchOffers(FilterRequest filterRequest, Pageable pageable);

  OfferServiceModel createOffer(OfferCreateRequest request, UUID creatorId) throws IOException;
}
