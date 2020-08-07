package bg.autohouse.service.services;

import bg.autohouse.data.models.media.MediaFile;
import bg.autohouse.data.models.media.MediaFunction;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.UUID;

public interface MediaFileService {
  String DEFAULT_MEDIA_BUCKET = "autohouse-media-files-general";
  String DEFAULT_OFFER_IMAGE_BUCKET = "autohouse-offer-images";

  MediaFile load(UUID id);

  List<MediaFile> loadForReference(UUID referenceId);

  MediaFile load(MediaFunction function, String imageKey);

  boolean doesFileExist(MediaFunction function, String imageKey);

  MediaFile storeFile(
      byte[] file,
      String fileKey,
      MediaFunction function,
      String contentType,
      String originalFilename,
      UUID referenceId);

  byte[] getBytes(final UUID uuid) throws IOException;

  byte[] getBytes(final MediaFile mediaFile) throws IOException;

  void downloadTo(final UUID uuid, final Path targetPath) throws IOException;

  void remove(final UUID uuid);

  void removeAllForReference(final UUID referenceId);
}
