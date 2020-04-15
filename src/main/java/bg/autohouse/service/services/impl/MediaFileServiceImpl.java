package bg.autohouse.service.services.impl;

import bg.autohouse.data.models.media.MediaFunction;
import bg.autohouse.data.repositories.MedialFileRepository;
import bg.autohouse.service.services.MediaFileService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class MediaFileServiceImpl implements MediaFileService {
  private final MedialFileRepository medialFileRepository;

  // @Override
  // @Transactional(readOnly = true)
  // public MediaFileRecord load(String uid) {
  //     return recordRepository.findOneByUid(uid);
  // }

  // @Override
  // @Transactional(readOnly = true)
  // public MediaFileRecord load(MediaFunction function, String imageKey) {
  //     return recordRepository.findByBucketAndKey(getBucketForFunction(function), imageKey);
  // }

  // @Override
  // public boolean doesFileExist(MediaFunction function, String imageKey) {
  //     return imageKey != null &&
  // recordRepository.findByBucketAndKey(getBucketForFunction(function), imageKey) != null;
  // }

  // @Override
  // @Transactional
  // public String storeFile(MultipartFile file, MediaFunction function, String mimeType, String
  // imageKey, String fileName) {
  //     String bucket = getBucketForFunction(Objects.requireNonNull(function));

  //     logger.info("storing a file, with function {}, bucket {}, content type: {}, passed mime
  // type: {}, file name: {}, original name: {}",
  //             function, bucket, file.getContentType(), mimeType, fileName,
  // file.getOriginalFilename());

  //     MediaFileRecord record = recordRepository.findByBucketAndKey(bucket, imageKey);
  //     String contentType = StringUtils.isEmpty(mimeType) ? file.getContentType() : mimeType;
  //     String nameToUse = StringUtils.isEmpty(fileName) ? file.getOriginalFilename() : fileName;
  //     if (record == null)
  //         record = new MediaFileRecord(bucket, contentType, imageKey, nameToUse, null);

  //     boolean fileStored = false;
  //     try {
  //         fileStored = storageBroker.storeMedia(record, file);
  //     } catch (SdkClientException e){
  //         logger.error("AWS SDK exception storing file ...",e.getMessage());
  //     }

  //     if (fileStored) {
  //         logger.info("media record stored and has mime type");
  //         record = recordRepository.save(record);
  //         return record.getUid();
  //     } else {
  //         logger.error("Error storing media file, returning null");
  //         return null;
  //     }
  // }

  // @Override
  // @Transactional
  // public String recordFile(String userUid, String bucket, String mimeType, String imageKey,
  // String fileName) {
  //     logger.info("Storing a file with bucket {}, key {}, mime type {}", bucket, imageKey,
  // mimeType);
  //     MediaFileRecord record = new MediaFileRecord(bucket, mimeType, imageKey, fileName,
  // userUid);
  //     record.setStoredTime(Instant.now());
  //     record = recordRepository.save(record);
  //     return record.getUid();
  // }

  @Override
  public String getBucketForFunction(MediaFunction function) {
    switch (function) {
      case OFFER_IMAGE:
      case OFFER_THUMBNAIL_IMAGE:
        return DEFAULT_OFFER_IMAGE_BUCKET;
      case USER_PROFILE_IMAGE:
        return DEFAULT_USER_IMAGE_BUCKET;
      default:
        return DEFAULT_MEDIA_BUCKET;
    }
  }
}
