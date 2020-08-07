package bg.autohouse.util;

import com.google.common.collect.ImmutableSet;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.multipart.MultipartFile;

import java.util.Set;

@Slf4j
public class ImageUtil {

  private static final Set<String> ALLOWED_ATTACHMENT_CONTENT_TYPE =
      ImmutableSet.of(MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE);

  public static String getMimiTypeExtension(String mimeType) {
    if (!isAcceptedMimeType(mimeType.toLowerCase())) {
      throw new IllegalArgumentException("Invalid file format Exception");
    }
    String mimeTypeExtension = mimeType.substring(mimeType.indexOf("/") + 1);
    mimeTypeExtension = "jpeg".equals(mimeTypeExtension) ? "jpg" : mimeTypeExtension;
    return mimeTypeExtension;
  }

  public static boolean isAcceptedMimeType(String mimeType) {
    return mimeType.endsWith("png") || mimeType.endsWith("jpg") || mimeType.endsWith("jpeg");
  }

  public static boolean isValidAttachmentContent(final MultipartFile file) {
    final String contentType = file.getContentType();
    if (!ALLOWED_ATTACHMENT_CONTENT_TYPE.contains(contentType)) {
      log.error("Invalid media type not allowed:" + contentType);
      return false;
    }
    return true;
  }

  private ImageUtil() {
    throw new AssertionError();
  }
}
