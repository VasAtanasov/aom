package bg.autohouse.web.controllers;

import bg.autohouse.config.WebConfiguration;
import bg.autohouse.data.models.media.MediaFile;
import bg.autohouse.data.models.media.MediaFunction;
import bg.autohouse.service.services.MediaFileService;
import bg.autohouse.util.ImageUtil;
import bg.autohouse.web.enums.RestMessage;
import bg.autohouse.web.util.RestUtil;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.InvalidMediaTypeException;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping(value = WebConfiguration.URL_IMAGES)
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class ImageFetchController extends BaseController {

  private final MediaFileService mediaFileService;

  @GetMapping(value = "/user/{userUid}")
  public ResponseEntity<byte[]> viewProfileImage(@PathVariable String userUid) throws IOException {
    String imageKey = generateFileKey(USER_PROFILE_IMAGE_FOLDER, userUid);
    MediaFile userImg = mediaFileService.load(MediaFunction.USER_PROFILE_IMAGE, imageKey);
    log.info("Fetched record: {}", userImg);
    return convertRecordToResponse(userImg);
  }

  @GetMapping(value = "/{mediaFunction}/{imageKey}")
  public ResponseEntity<byte[]> fetchImage(
      @PathVariable MediaFunction mediaFunction, @PathVariable String imageKey) throws IOException {
    MediaFile record = mediaFileService.load(mediaFunction, imageKey);
    log.info("record retrieved: {}", record);
    return convertRecordToResponse(record);
  }

  private ResponseEntity<byte[]> convertRecordToResponse(MediaFile record) throws IOException {
    byte[] data = mediaFileService.getBytes(record.getId());
    HttpHeaders headers = new HttpHeaders();
    try {
      headers.setContentType(MediaType.parseMediaType(record.getContentType()));
    } catch (InvalidMediaTypeException e) {
      log.info("error processing mime type, record has: {}", record.getContentType());
      log.error("couldn't set MIME heading ...", e);
    }
    String filename = ImageUtil.generateFileName(record);
    log.debug("file name : {}", filename);
    headers.setContentDispositionFormData(filename, filename);
    headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
    headers.setContentLength(data.length);
    return ResponseEntity.ok().headers(headers).body(data);
  }

  @ExceptionHandler(IOException.class)
  public ResponseEntity<?> noImageResponse(IOException e) {
    log.error("IO Exception in image fetching: ", e);

    return RestUtil.errorResponse(
        HttpStatus.INTERNAL_SERVER_ERROR, RestMessage.ERROR_WHILE_FETCHING_IMAGE);
  }
}
