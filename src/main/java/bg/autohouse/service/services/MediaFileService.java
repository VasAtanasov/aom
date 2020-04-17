package bg.autohouse.service.services;

import bg.autohouse.data.models.media.MediaFile;
import bg.autohouse.data.models.media.MediaFunction;
import java.io.IOException;
import java.nio.file.Path;
import org.springframework.web.multipart.MultipartFile;

public interface MediaFileService {
  public static final String DEFAULT_MEDIA_BUCKET = "autohouse-media-files-general";
  public static final String DEFAULT_OFFER_IMAGE_BUCKET = "autohouse-offer-images";

  MediaFile load(String id);

  MediaFile load(MediaFunction function, String imageKey);

  boolean doesFileExist(MediaFunction function, String imageKey);

  String storeFile(
      MultipartFile file,
      MediaFunction function,
      String mimeType,
      String imageKey,
      String fileName);

  byte[] getBytes(final String uuid) throws IOException;

  void downloadTo(final String uuid, final Path targetPath) throws IOException;

  void remove(final String uuid);
}
