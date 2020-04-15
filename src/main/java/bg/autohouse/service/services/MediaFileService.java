package bg.autohouse.service.services;

import bg.autohouse.data.models.media.MediaFunction;

public interface MediaFileService {
  public static final String DEFAULT_MEDIA_BUCKET = "autohouse-media-files-general";
  public static final String DEFAULT_OFFER_IMAGE_BUCKET = "offer-images-staging";
  public static final String DEFAULT_USER_IMAGE_BUCKET = "user-profile-images-staging";

  String getBucketForFunction(MediaFunction function);
}
