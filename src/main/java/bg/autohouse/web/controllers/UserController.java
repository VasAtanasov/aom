package bg.autohouse.web.controllers;

import static bg.autohouse.config.WebConfiguration.APP_V1_MEDIA_TYPE_JSON;

import bg.autohouse.config.WebConfiguration;
import bg.autohouse.data.models.User;
import bg.autohouse.data.models.enums.UserLogType;
import bg.autohouse.data.models.media.MediaFile;
import bg.autohouse.data.models.media.MediaFunction;
import bg.autohouse.security.authentication.LoggedUser;
import bg.autohouse.service.models.offer.OfferServiceModel;
import bg.autohouse.service.services.AsyncUserLogger;
import bg.autohouse.service.services.MediaFileService;
import bg.autohouse.service.services.OfferService;
import bg.autohouse.service.services.PasswordService;
import bg.autohouse.service.services.UserService;
import bg.autohouse.util.ImageResizer;
import bg.autohouse.util.ImageUtil;
import bg.autohouse.util.ModelMapperWrapper;
import bg.autohouse.web.enums.RestMessage;
import bg.autohouse.web.models.request.UserChangePasswordRequest;
import bg.autohouse.web.models.response.offer.OfferResponseModel;
import bg.autohouse.web.util.RestUtil;
import java.io.IOException;
import java.util.UUID;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RestController
@RequestMapping(WebConfiguration.URL_USER_BASE)
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@PreAuthorize("hasRole('USER')")
public class UserController extends BaseController {

  private final PasswordService passwordService;
  private final UserService userService;
  private final OfferService offerService;
  private final MediaFileService mediaFileService;
  private final AsyncUserLogger userLogger;
  private final ImageResizer imageResizer;
  private final ModelMapperWrapper modelMapper;

  @PostMapping(value = "/image/change")
  public ResponseEntity<?> uploadProfileImage(
      @RequestBody MultipartFile photo, @LoggedUser User user) throws IOException {
    String contentType = photo.getContentType();
    boolean acceptable = ImageUtil.isAcceptedMimeType(contentType);
    if (!acceptable) return RestUtil.errorResponse(RestMessage.INVALID_MEDIA_TYPE);
    log.info("storing a media file, with mediaFunction = {}", MediaFunction.USER_PROFILE_IMAGE);
    String imageKey = generateFileKey(USER_PROFILE_IMAGE_FOLDER, user.getId().toString());
    byte[] biteArray = imageResizer.toJpgDownscaleToSize(photo.getInputStream());
    MediaFile storedFile =
        mediaFileService.storeFile(
            biteArray,
            imageKey,
            MediaFunction.USER_PROFILE_IMAGE,
            photo.getContentType(),
            photo.getOriginalFilename(),
            user.getId());
    userService.updateHasImage(user.getId(), true);
    userLogger.recordUserLog(user.getId(), UserLogType.USER_CHANGE_PROFILE_PHOTO, imageKey);
    return RestUtil.okResponse(
        RestMessage.IMAGE_UPLOAD_SUCCESSFUL, toMap("mediaUid", storedFile.getId()));
  }

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
