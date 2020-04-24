package bg.autohouse.service.services;

import bg.autohouse.data.models.media.MediaFile;
import bg.autohouse.data.models.media.MediaFunction;
import java.io.IOException;
import java.nio.file.Path;
import java.util.UUID;
import org.springframework.web.multipart.MultipartFile;

public interface MediaFileService {
  public static final String DEFAULT_MEDIA_BUCKET = "autohouse-media-files-general";
  public static final String DEFAULT_OFFER_IMAGE_BUCKET = "autohouse-offer-images";

  MediaFile load(UUID id);

  MediaFile load(MediaFunction function, String imageKey);

  boolean doesFileExist(MediaFunction function, String imageKey);

  UUID storeFile(
      UUID fileUuid,
      MultipartFile file,
      String fileKey,
      MediaFunction function,
      String originalFilename,
      UUID referenceId);

  byte[] getBytes(final UUID uuid) throws IOException;

  void downloadTo(final UUID uuid, final Path targetPath) throws IOException;

  void remove(final UUID uuid);
}