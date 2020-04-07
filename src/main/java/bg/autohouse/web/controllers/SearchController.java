package bg.autohouse.web.controllers;

import static bg.autohouse.config.WebConfiguration.APP_V1_MEDIA_TYPE_JSON;

import bg.autohouse.config.WebConfiguration;
import bg.autohouse.service.services.OfferService;
import bg.autohouse.util.ModelMapperWrapper;
import bg.autohouse.util.PaginationHeadersUtils;
import bg.autohouse.web.enums.RestMessage;
import bg.autohouse.web.models.request.FilterRequest;
import bg.autohouse.web.models.response.OfferResponseModel;
import bg.autohouse.web.models.response.ResponseWrapper;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(WebConfiguration.OFFERS)
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class SearchController extends BaseController {

  private final OfferService offerService;
  private final ModelMapperWrapper modelMapper;

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

    Page<OfferResponseModel> page =
        offerService
            .searchOffers(filterRequest, pageable)
            .map(o -> modelMapper.map(o, OfferResponseModel.class));

    ResponseWrapper response =
        ResponseWrapper.builder()
            .success(Boolean.TRUE)
            .message(String.valueOf(RestMessage.REQUEST_SUCCESS))
            .data(toMap("page", page))
            .status(HttpStatus.OK.value())
            .build();

    return ResponseEntity.ok()
        .headers(PaginationHeadersUtils.buildPaginationHeaders(page))
        .body(response);
  }
}