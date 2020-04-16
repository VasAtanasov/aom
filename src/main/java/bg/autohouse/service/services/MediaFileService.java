package bg.autohouse.service.services;

import bg.autohouse.data.models.media.MediaFile;
import bg.autohouse.data.models.media.MediaFunction;
import org.springframework.web.multipart.MultipartFile;

public interface MediaFileService {
  public static final String DEFAULT_MEDIA_BUCKET = "autohouse-media-files-general";
  public static final String DEFAULT_OFFER_IMAGE_BUCKET = "offer-images-staging";

  MediaFile load(String id);

  String getBucketForFunction(MediaFunction function);

  MediaFile load(MediaFunction function, String imageKey);

  boolean doesFileExist(MediaFunction function, String imageKey);

  String storeFile(
      MultipartFile file,
      MediaFunction function,
      String mimeType,
      String imageKey,
      String fileName);

  String recordFile(
      String userUid, String bucket, String mimeType, String imageKey, String fileName);
}
