package bg.autohouse.web.controllers;

import static bg.autohouse.common.Constants.*;
import static bg.autohouse.config.WebConfiguration.APP_V1_MEDIA_TYPE_JSON;

import bg.autohouse.config.WebConfiguration;
import bg.autohouse.service.models.OfferTopServiceModel;
import bg.autohouse.service.services.OfferService;
import bg.autohouse.util.ModelMapperWrapper;
import bg.autohouse.web.models.request.FilterRequest;
import bg.autohouse.web.models.request.OfferCreateRequest;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
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
  private final ModelMapperWrapper modelMapper;

  // TODO authenticated users can create offers add authenticated principals
  @PostMapping(
      produces = {APP_V1_MEDIA_TYPE_JSON},
      consumes = {APP_V1_MEDIA_TYPE_JSON})
  public ResponseEntity<?> createOffer(@Valid @RequestBody OfferCreateRequest createRequest) {

    return ResponseEntity.ok().build();
  }

  @PostMapping(
      value = "/search",
      produces = {APP_V1_MEDIA_TYPE_JSON},
      consumes = {APP_V1_MEDIA_TYPE_JSON})
  public ResponseEntity<?> findOffers(
      @Valid @RequestBody FilterRequest filterRequest,
      @PageableDefault(
              page = DEFAULT_PAGE_NUMBER,
              size = DEFAULT_PAGE_SIZE,
              sort = SORT,
              direction = Sort.Direction.DESC)
          Pageable pageable) {
    Page<OfferTopServiceModel> page = offerService.searchOffers(filterRequest, pageable);
    return handleRequestSuccess(page, REQUEST_SUCCESS);
  }
}
