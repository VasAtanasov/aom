package bg.autohouse.web.controllers;

import static bg.autohouse.config.WebConfiguration.APP_V1_MEDIA_TYPE_JSON;

import bg.autohouse.config.WebConfiguration;
import bg.autohouse.data.models.User;
import bg.autohouse.security.authentication.LoggedUser;
import bg.autohouse.service.models.offer.OfferServiceModel;
import bg.autohouse.service.services.OfferService;
import bg.autohouse.web.models.request.offer.OfferCreateRequest;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(WebConfiguration.OFFERS)
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class OfferController extends BaseController {

  private final OfferService offerService;
  // private final ModelMapperWrapper modelMapper;

  @PostMapping(
      produces = {APP_V1_MEDIA_TYPE_JSON},
      consumes = {APP_V1_MEDIA_TYPE_JSON, MediaType.MULTIPART_FORM_DATA_VALUE})
  public ResponseEntity<?> createOffer(
      @Valid @RequestBody OfferCreateRequest createRequest, @LoggedUser User creator) {
    OfferServiceModel offerServiceModel = offerService.createOffer(createRequest, creator.getId());
    return ResponseEntity.ok(offerServiceModel);
  }
}
