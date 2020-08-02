package bg.autohouse.web.controllers;

import static bg.autohouse.config.WebConfiguration.APP_V1_MEDIA_TYPE_JSON;

import bg.autohouse.config.WebConfiguration;
import bg.autohouse.data.models.User;
import bg.autohouse.security.authentication.LoggedUser;
import bg.autohouse.service.models.offer.OfferServiceModel;
import bg.autohouse.service.services.OfferService;
import bg.autohouse.service.services.PasswordService;
import bg.autohouse.service.services.UserService;
import bg.autohouse.util.ModelMapperWrapper;
import bg.autohouse.web.enums.RestMessage;
import bg.autohouse.web.models.request.UserChangePasswordRequest;
import bg.autohouse.web.models.response.offer.OfferResponseModel;
import bg.autohouse.web.util.RestUtil;
import java.util.UUID;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(WebConfiguration.URL_USER_BASE)
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@PreAuthorize("hasRole('USER')")
public class UserController extends BaseController {

  private final PasswordService passwordService;
  private final UserService userService;
  private final OfferService offerService;
  private final ModelMapperWrapper modelMapper;

  @PostMapping(
      value = "/password/update",
      produces = {APP_V1_MEDIA_TYPE_JSON},
      consumes = {APP_V1_MEDIA_TYPE_JSON})
  public ResponseEntity<?> changeUserPassword(
      @Valid @RequestBody UserChangePasswordRequest request, @LoggedUser User user) {
    if (!request.getNewPassword().equals(request.getConfirmPassword())) {
      return RestUtil.errorResponse(RestMessage.INVALID_PASSWORD);
    }
    boolean isChanged =
        passwordService.changeUserPassword(
            user.getId(), request.getOldPassword(), request.getNewPassword());
    if (isChanged) return ResponseEntity.ok().build();
    return RestUtil.errorResponse(RestMessage.INVALID_PASSWORD);
  }

  @GetMapping(
      value = "/offer/add-to-favorites/{offerId}",
      produces = {APP_V1_MEDIA_TYPE_JSON})
  public ResponseEntity<?> addToFavorites(@PathVariable UUID offerId, @LoggedUser User creator) {
    return ResponseEntity.ok(userService.addToFavorites(creator.getId(), offerId));
  }

  @GetMapping(
      value = "/offer/list",
      produces = {APP_V1_MEDIA_TYPE_JSON})
  public ResponseEntity<?> getUserOffers(
      @LoggedUser User creator,
      @PageableDefault(
              page = DEFAULT_PAGE_NUMBER,
              size = 15,
              sort = SORT,
              direction = Sort.Direction.DESC)
          Pageable pageable) {
    Page<OfferServiceModel> userOffers = offerService.findUserOffers(creator.getId(), pageable);
    return ResponseEntity.ok(
        userOffers.map(offer -> modelMapper.map(offer, OfferResponseModel.class)));
  }

  @GetMapping(
      value = "/offer/toggle-active/{offerId}",
      produces = {APP_V1_MEDIA_TYPE_JSON})
  public ResponseEntity<?> toggleOfferActive(@PathVariable UUID offerId, @LoggedUser User creator) {
    return ResponseEntity.ok(offerService.toggleActive(creator.getId(), offerId));
  }

  // TODO delete user profile
}
