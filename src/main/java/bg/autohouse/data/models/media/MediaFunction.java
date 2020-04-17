package bg.autohouse.data.models.media;

import bg.autohouse.service.services.MediaFileService;

public enum MediaFunction {
  OFFER_IMAGE(StorageType.DROPBOX_BUCKET),
  OFFER_THUMBNAIL_IMAGE(StorageType.DROPBOX_BUCKET),
  USER_PROFILE_IMAGE(StorageType.LOCAL_DATABASE),
  GENERAL_UNKNOWN(StorageType.LOCAL_FOLDER);

  private final StorageType storageType;

  MediaFunction(final StorageType storageType) {
    this.storageType = storageType;
  }

  public StorageType storageType() {
    return storageType;
  }

  public String formatFilename(final MediaFile mediaFile) {
    switch (this) {
      case USER_PROFILE_IMAGE:
        return mediaFile.getReferenceId();
      default:
        return mediaFile.getId().toString() + "_" + System.currentTimeMillis();
    }
  }

  public String resolveBucketName() {
    return this == MediaFunction.OFFER_IMAGE || this == MediaFunction.OFFER_THUMBNAIL_IMAGE
        ? MediaFileService.DEFAULT_OFFER_IMAGE_BUCKET
        : MediaFileService.DEFAULT_MEDIA_BUCKET;
  }
}
