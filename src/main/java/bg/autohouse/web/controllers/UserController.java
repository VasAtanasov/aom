package bg.autohouse.web.controllers;

import static bg.autohouse.config.WebConfiguration.APP_V1_MEDIA_TYPE_JSON;

import bg.autohouse.config.WebConfiguration;
import bg.autohouse.data.models.User;
import bg.autohouse.security.authentication.LoggedUser;
import bg.autohouse.service.services.PasswordService;
import bg.autohouse.web.enums.RestMessage;
import bg.autohouse.web.models.request.UserChangePasswordRequest;
import bg.autohouse.web.util.RestUtil;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(WebConfiguration.URL_USER_BASE)
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@PreAuthorize("hasRole('USER')")
public class UserController extends BaseController {

  private static final String USER_PROFILE_IMAGE_FOLDER = "user-profile-images-staging}";

  private final PasswordService passwordService;

  // @RequestMapping(value = "/image/change", method = RequestMethod.POST)
  // public ResponseEntity uploadProfileImage(@RequestBody MultipartFile photo, HttpServletRequest
  // request) {
  //     String userUid = getUserIdFromRequest(request);
  //     String imageKey = userProfileImagesFolder + "/" + userUid;

  //     log.info("storing a media file, with imageKey = {}, and mediaFunction = {}", imageKey,
  // MediaFunction.USER_PROFILE_IMAGE);

  //     String storedFileUid = mediaFileBroker.storeFile(photo, MediaFunction.USER_PROFILE_IMAGE,
  // null, imageKey, photo.getName());
  //     userService.updateHasImage(userUid, true);
  //     MediaUploadResult result =
  // MediaUploadResult.builder().mediaRecordUid(storedFileUid).build();
  //     return ResponseEntity.ok(result);
  // }

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

  // TODO update profile image
  // TODO update user data
  // TODO delete user profile
}
