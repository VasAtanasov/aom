package bg.autohouse.web.controllers;

import bg.autohouse.config.WebConfiguration;
import bg.autohouse.data.models.media.MediaFile;
import bg.autohouse.data.models.media.MediaFunction;
import bg.autohouse.service.services.MediaFileService;
import bg.autohouse.util.ImageResizer;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
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
  private final ImageResizer imageResizer;

  @GetMapping(value = "/{mediaFunction}/{imageKey}")
  public ResponseEntity<byte[]> fetchImage(
      @PathVariable MediaFunction mediaFunction, @PathVariable String imageKey) throws IOException {
    MediaFile record = mediaFileService.load(mediaFunction, imageKey);
    log.info("record retrieved: {}", record);
    return convertRecordToResponse(record, false);
  }

  @GetMapping(value = "/{folder}/{year}/{month}/{day}/{offerId}/{fileName:.+}")
  public ResponseEntity<byte[]> fetchOfferImage(
      @PathVariable String folder,
      @PathVariable String year,
      @PathVariable String month,
      @PathVariable String day,
      @PathVariable String offerId,
      @PathVariable String fileName,
      @RequestParam(required = false) boolean thumbnail)
      throws IOException {
    String imageKey = generateFileKey(folder, year, month, day, offerId, fileName);
    MediaFile record = mediaFileService.load(MediaFunction.OFFER_IMAGE, imageKey);
    return convertRecordToResponse(record, thumbnail);
  }

  private ResponseEntity<byte[]> convertRecordToResponse(MediaFile record, boolean thumbnail)
      throws IOException {
    byte[] data = mediaFileService.getBytes(record);
    if (thumbnail) data = imageResizer.createThumbnail(data, 320, 240);
    HttpHeaders headers = new HttpHeaders();
    try {
      headers.setContentType(MediaType.parseMediaType(record.getContentType()));
    } catch (InvalidMediaTypeException e) {
      log.info("error processing mime type, record has: {}", record.getContentType());
      log.error("couldn't set MIME heading ...", e);
    }
    String filename = record.getFileKey().substring(record.getFileKey().lastIndexOf("/") + 1);
    log.debug("file name : {}", filename);
    headers.setContentDispositionFormData(filename, filename);
    headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
    headers.setContentLength(data.length);
    return ResponseEntity.ok().headers(headers).body(data);
  }
}
