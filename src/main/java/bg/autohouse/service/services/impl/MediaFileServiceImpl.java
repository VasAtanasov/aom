package bg.autohouse.service.services.impl;

import bg.autohouse.data.models.media.MediaFile;
import bg.autohouse.data.models.media.MediaFunction;
import bg.autohouse.data.repositories.MedialFileRepository;
import bg.autohouse.service.services.MediaFileService;
import bg.autohouse.service.services.StorageService;
import bg.autohouse.util.Assert;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class MediaFileServiceImpl implements MediaFileService {
  private final MedialFileRepository medialFileRepository;
  private final StorageService cloudService;

  @Override
  @Transactional(readOnly = true)
  public MediaFile load(String id) {
    return medialFileRepository.getOne(id);
  }

  @Override
  @Transactional(readOnly = true)
  public MediaFile load(MediaFunction function, String imageKey) {
    return medialFileRepository.findByBucketAndFileKey(getBucketForFunction(function), imageKey);
  }

  @Override
  public boolean doesFileExist(MediaFunction function, String imageKey) {
    return medialFileRepository.existsByBucketAndFileKey(getBucketForFunction(function), imageKey);
  }

  @Override
  @Transactional
  public String storeFile(
      MultipartFile file,
      MediaFunction function,
      String mimeType,
      String imageKey,
      String fileName) {

    Assert.notNull(function, "Unspecified media function.");

    String bucket = getBucketForFunction(function);

    log.info(
        "storing a file, with function {}, bucket {}, content type: {}, passed mime type: {}, file name: {}, original name: {}",
        function,
        bucket,
        file.getContentType(),
        mimeType,
        fileName,
        file.getOriginalFilename());

    MediaFile record = medialFileRepository.findByBucketAndFileKey(bucket, imageKey);
    String contentType = !Assert.has(mimeType) ? file.getContentType() : mimeType;
    String nameToUse = !Assert.has(mimeType) ? file.getOriginalFilename() : fileName;
    if (record == null)
      record =
          MediaFile.builder()
              .bucket(bucket)
              .contentType(contentType)
              .fileKey(imageKey)
              .fileName(nameToUse)
              .build();

    boolean fileStored = cloudService.storeMedia(record, file);

    if (fileStored) {
      log.info("media record stored and has mime type");
      record = medialFileRepository.save(record);
      return record.getId();
    } else {
      log.error("Error storing media file, returning null");
      return null;
    }
  }

  @Override
  @Transactional
  public String recordFile(
      String userUid, String bucket, String mimeType, String imageKey, String fileName) {
    log.info("Storing a file with bucket {}, key {}, mime type {}", bucket, imageKey, mimeType);
    MediaFile record =
        MediaFile.builder()
            .bucket(bucket)
            .contentType(mimeType)
            .fileKey(imageKey)
            .fileName(fileName)
            .build();

    record = medialFileRepository.save(record);
    return record.getId();
  }

  @Override
  public String getBucketForFunction(MediaFunction function) {
    switch (function) {
      case OFFER_IMAGE:
      case OFFER_THUMBNAIL_IMAGE:
        return DEFAULT_OFFER_IMAGE_BUCKET;
      case USER_PROFILE_IMAGE:
        return DEFAULT_MEDIA_BUCKET;
      default:
        return DEFAULT_MEDIA_BUCKET;
    }
  }
}
