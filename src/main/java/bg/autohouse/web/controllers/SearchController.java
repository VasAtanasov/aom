package bg.autohouse.web.controllers;

import static bg.autohouse.config.WebConfiguration.APP_V1_MEDIA_TYPE_JSON;

import bg.autohouse.config.WebConfiguration;
import bg.autohouse.data.models.User;
import bg.autohouse.security.authentication.LoggedUser;
import bg.autohouse.service.services.FilterService;
import bg.autohouse.service.services.OfferService;
import bg.autohouse.util.ModelMapperWrapper;
import bg.autohouse.util.PaginationHeadersUtils;
import bg.autohouse.web.enums.RestMessage;
import bg.autohouse.web.models.request.FilterRequest;
import bg.autohouse.web.models.response.FilterResponseModel;
import bg.autohouse.web.models.response.ResponseWrapper;
import bg.autohouse.web.models.response.offer.OfferResponseModel;
import java.util.List;
import java.util.UUID;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(WebConfiguration.OFFERS)
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class SearchController extends BaseController {

  private final OfferService offerService;
  private final FilterService filterService;
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
    UUID filterRequestId = filterService.createFilter(filterRequest);
    Page<OfferResponseModel> page =
        offerService
            .searchOffers(filterRequestId, pageable)
            .map(o -> modelMapper.map(o, OfferResponseModel.class));
    ResponseWrapper response =
        ResponseWrapper.builder()
            .success(Boolean.TRUE)
            .message(RestMessage.REQUEST_SUCCESS)
            .data(toMap("page", page))
            .status(HttpStatus.OK.value())
            .build();
    return ResponseEntity.ok()
        .headers(PaginationHeadersUtils.buildPaginationHeaders(page))
        .body(response);
  }

  @PostMapping(
      value = "/search/favorites",
      produces = {APP_V1_MEDIA_TYPE_JSON},
      consumes = {APP_V1_MEDIA_TYPE_JSON})
  public ResponseEntity<?> findOffersByIds(
      @RequestBody List<UUID> offerIds,
      @PageableDefault(
              page = DEFAULT_PAGE_NUMBER,
              size = DEFAULT_PAGE_SIZE,
              sort = SORT,
              direction = Sort.Direction.DESC)
          Pageable pageable) {
    Page<OfferResponseModel> page =
        offerService
            .searchOffersByIds(offerIds, pageable)
            .map(o -> modelMapper.map(o, OfferResponseModel.class));
    return ResponseEntity.ok()
        .headers(PaginationHeadersUtils.buildPaginationHeaders(page))
        .body(page);
  }

  @PostMapping(
      value = "/search/save",
      produces = {APP_V1_MEDIA_TYPE_JSON},
      consumes = {APP_V1_MEDIA_TYPE_JSON})
  public ResponseEntity<?> saveSearch(
      @Valid @RequestBody FilterRequest filterRequest, @LoggedUser User creator) {
    filterService.saveSearch(filterRequest, creator.getId());
    return ResponseEntity.ok().build();
  }

  @GetMapping(
      value = "/search/list",
      produces = {APP_V1_MEDIA_TYPE_JSON})
  public ResponseEntity<?> listSavedSearches(@LoggedUser User user) {
    List<FilterResponseModel> savedSearches =
        modelMapper.mapAll(
            filterService.listSavedSearches(user.getId()), FilterResponseModel.class);
    return ResponseEntity.ok(savedSearches);
  }

  @DeleteMapping(value = "/search/saved-search/{filterId}")
  public ResponseEntity<?> deleteSavedSearch(
      @PathVariable UUID filterId, @LoggedUser User creator) {
    boolean isRemoved = filterService.deleteSavedSearch(filterId, creator.getId());
    if (!isRemoved) return ResponseEntity.notFound().build();
    return ResponseEntity.ok(filterId);
  }
}
