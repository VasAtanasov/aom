package bg.autohouse.web.controllers;

import bg.autohouse.config.WebConfiguration;
import bg.autohouse.service.services.MediaFileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping(value = WebConfiguration.URL_MEDIA)
@PreAuthorize("hasRole('USER')")
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class MediaController {

  private final MediaFileService mediaService;

  // @PostMapping(value = "/store/body/{mediaFunction}")
  // public ResponseEntity<?> storeMediaFileWeb(
  //     @PathVariable MediaFunction mediaFunction, @RequestBody MultipartFile file) {
  //   log.info("Media function: {}, File Name: {}", mediaFunction, file.getOriginalFilename());

  //   log.info(
  //       "storing a media file, with imageKey = {}, and mediaFunction = {}",
  //       imageKey,
  //       mediaFunction);

  //   return storeMediaFile(mediaFunction, imageKey, mimeType, file);
  // }

  // @RequestMapping(value = "/store/record", method = RequestMethod.POST)
  // public ResponseEntity recordMediaFile(HttpServletRequest request,
  //                                        @RequestParam String bucket,
  //                                        @RequestParam String imageKey,
  //                                        @RequestParam String mimeType) {
  //     log.info("Recording a media file, with image key {}, and bucket {}", imageKey, bucket);
  //     String storedFileUid = mediaFileBroker.recordFile(getUserIdFromRequest(request), bucket,
  // mimeType, imageKey, null);
  //     log.info("Stored, with id = {}", storedFileUid);
  //     return ResponseEntity.ok(storedFileUid);
  // }

  // TODO implement service and response models for media file
  @GetMapping(value = "/details/{mediaRecordId}")
  public ResponseEntity<?> fetchMediaRecordDetails(@PathVariable String mediaRecordId) {
    log.info("Fetching media record with id: {}", mediaRecordId);
    return ResponseEntity.ok(mediaService.load(mediaRecordId));
  }

  // private ResponseEntity<ResponseWrapper> storeMediaFile(
  //     @PathVariable MediaFunction mediaFunction,
  //     @RequestParam(required = false) String imageKey,
  //     @RequestParam(required = false) String mimeType,
  //     @RequestParam MultipartFile file) {
  //   // store the media, depending on its function (if task image stick in there so analysis etc
  // is
  //   // triggered)
  //   log.info(
  //       "storing a media file, with imageKey = {}, and mediaFunction = {}",
  //       imageKey,
  //       mediaFunction);
  //   boolean duplicate = mediaFileBroker.doesFileExist(mediaFunction, imageKey);
  //   String storedFileUid =
  //       duplicate
  //           ? mediaFileBroker.load(mediaFunction, imageKey).getUid()
  //           : mediaFileBroker.storeFile(
  //               file, mediaFunction, mimeType, imageKey, file.getOriginalFilename());
  //   return RestUtil.okayResponseWithData(
  //       duplicate ? RestMessage.ALREADY_EXISTS : RestMessage.UPLOADED, storedFileUid);
  // }
}
