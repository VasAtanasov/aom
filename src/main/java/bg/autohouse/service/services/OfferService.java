package bg.autohouse.service.services;

import bg.autohouse.service.models.offer.OfferDetailsServiceModel;
import bg.autohouse.service.models.offer.OfferEditServiceModel;
import bg.autohouse.service.models.offer.OfferServiceModel;
import bg.autohouse.web.models.request.offer.OfferCreateRequest;
import java.io.IOException;
import java.util.List;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface OfferService {
  List<OfferServiceModel> getLatestOffers();

  OfferDetailsServiceModel loadOfferByIdPublicView(UUID id);

  OfferDetailsServiceModel loadOfferByIdPrivateView(UUID id, UUID creatorId);

  OfferEditServiceModel loadOfferForEdit(UUID creatorId, UUID offerId);

  List<String> fetchOfferImages(UUID offerId);

  Page<OfferServiceModel> searchOffers(UUID filterId, Pageable pageable);

  OfferServiceModel createOffer(OfferCreateRequest request, UUID creatorId) throws IOException;

  OfferServiceModel updateOffer(OfferCreateRequest createRequest, UUID offerId, UUID id);

  Page<OfferServiceModel> searchOffersByIds(List<UUID> offerIds, Pageable pageable);

  Page<OfferServiceModel> findUserOffers(UUID userId, Pageable pageable);

  boolean toggleActive(UUID creatorId, UUID offerId);

  void deleteOffer(UUID userId, UUID offerId);
}
