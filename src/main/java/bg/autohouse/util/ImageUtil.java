package bg.autohouse.util;

import bg.autohouse.data.models.media.MediaFile;

public class ImageUtil {

  public static String generateFileName(MediaFile record) {
    String extension = getMimiTypeExtension(record.getContentType());
    return "image-" + record.getFileKey() + "." + extension;
  }

  public static String getMimiTypeExtension(String mimeType) {

    if (!isAcceptedMimeType(mimeType.toLowerCase())) {
      throw new IllegalArgumentException("Invalid file format Exception");
    }
    String mimeTypeExtension = mimeType.substring(mimeType.indexOf("/") + 1, mimeType.length());
    mimeTypeExtension = "jpeg".equals(mimeTypeExtension) ? "jpg" : mimeTypeExtension;

    return mimeTypeExtension;
  }

  public static String getMimeType(String imageUrl) {
    // for now images in the database guaranteed to be either in jpeg or png format
    if (!(imageUrl.endsWith("png") || imageUrl.endsWith("jpg"))) {
      return "image/jpeg";
    }
    return "image/png";
  }

  public static boolean isAcceptedMimeType(String mimeType) {
    return mimeType.endsWith("png") || mimeType.endsWith("jpg") || mimeType.endsWith("jpeg");
  }
}
