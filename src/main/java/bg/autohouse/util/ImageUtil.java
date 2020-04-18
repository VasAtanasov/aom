package bg.autohouse.util;

import bg.autohouse.data.models.media.MediaFile;
import com.google.common.collect.ImmutableSet;
import java.util.Set;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
public class ImageUtil {

  private static final Set<String> ALLOWED_ATTACHMENT_CONTENT_TYPE =
      ImmutableSet.of(MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE);

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

  public boolean isValidAttachmentContent(final MultipartFile file) {
    final String contentType = file.getContentType();

    if (!ALLOWED_ATTACHMENT_CONTENT_TYPE.contains(contentType)) {
      log.error("Invalid media type not allowed:" + contentType);
      return false;
    }

    return true;
  }
}
