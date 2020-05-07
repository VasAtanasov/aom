package bg.autohouse.web.controllers;

import static bg.autohouse.config.WebConfiguration.APP_V1_MEDIA_TYPE_JSON;

import bg.autohouse.config.WebConfiguration;
import bg.autohouse.data.models.User;
import bg.autohouse.data.repositories.OfferRepository;
import bg.autohouse.security.authentication.LoggedUser;
import bg.autohouse.service.models.offer.OfferServiceModel;
import bg.autohouse.service.services.OfferService;
import bg.autohouse.util.ModelMapperWrapper;
import bg.autohouse.web.models.request.offer.OfferCreateRequest;
import bg.autohouse.web.models.response.offer.OfferResponseModel;
import bg.autohouse.web.util.RestUtil;
import java.io.IOException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(WebConfiguration.OFFERS)
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class OfferController extends BaseController {

  private final OfferService offerService;
  private final ModelMapperWrapper modelMapper;
  private final OfferRepository offerRepository;

  @PostMapping(
      produces = {APP_V1_MEDIA_TYPE_JSON},
      consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
  public ResponseEntity<?> createOffer(
      @ModelAttribute OfferCreateRequest createRequest, @LoggedUser User creator)
      throws IOException {
    OfferServiceModel offerServiceModel = offerService.createOffer(createRequest, creator.getId());
    return ResponseEntity.ok(offerServiceModel);
  }

  @GetMapping(
      value = "/top",
      produces = {APP_V1_MEDIA_TYPE_JSON})
  public ResponseEntity<?> getLatestOffers() {
    List<OfferResponseModel> topOffers =
        modelMapper.mapAll(offerService.getLatestOffers(), OfferResponseModel.class);
    return RestUtil.okResponse(topOffers);
  }

  @GetMapping(
      value = "/statistics",
      produces = {APP_V1_MEDIA_TYPE_JSON})
  public ResponseEntity<?> getOfferStatistics() {
    return ResponseEntity.ok(offerRepository.getStatistics());
  }

  @GetMapping(
      value = "/count/body-styles",
      produces = {APP_V1_MEDIA_TYPE_JSON})
  public ResponseEntity<?> getBodyStylesOffersCount() {
    return ResponseEntity.ok(offerRepository.getCountStatistics());
  }
}
