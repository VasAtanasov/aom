package bg.autohouse.web.controllers;

// @RequestMapping(value = "/media")
// @PreAuthorize("hasRole('USER')")
public class MediaController {

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

  // @RequestMapping(value = "/store/body/{mediaFunction}", method = RequestMethod.POST)
  // public ResponseEntity<ResponseWrapper> storeMediaFileWeb(@PathVariable MediaFunction
  // mediaFunction,
  //                                                          @RequestParam(required = false) String
  // imageKey,
  //                                                          @RequestParam(required = false) String
  // mimeType,
  //                                                          @RequestBody MultipartFile file) {
  //     log.info("Media function: {}, File Name: {}", mediaFunction, file.getOriginalFilename());
  //     return storeMediaFile(mediaFunction, imageKey, mimeType, file);
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

  // @RequestMapping(value = "/details/{mediaRecordId}", method = RequestMethod.GET)
  // public ResponseEntity<MediaFileRecord> fetchMediaRecordDetails(@PathVariable String
  // mediaRecordId) {
  //     return ResponseEntity.ok(mediaFileBroker.load(mediaRecordId));
  // }
}
