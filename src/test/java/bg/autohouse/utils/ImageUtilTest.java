package bg.autohouse.utils;

import bg.autohouse.util.ImageUtil;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

public class ImageUtilTest {

  @Test
  void when_getMimiTypeExtension_validExtension_shouldReturn() {
    String extension = ImageUtil.getMimiTypeExtension(MediaType.IMAGE_JPEG_VALUE);
    assertThat(extension).isEqualToIgnoringCase("JPG");
  }

  @Test
  void when_getMimiTypeExtension_invalidExtension_shouldThrow() {
    Throwable thrown =
        catchThrowable(() -> ImageUtil.getMimiTypeExtension(MediaType.IMAGE_GIF_VALUE));
    assertThat(thrown)
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageStartingWith("Invalid file format Exception");
  }

  @Test
  void test_isValidAttachmentContent() {
    MockMultipartFile validContent =
        new MockMultipartFile(
            "images", "image.jpg", MediaType.IMAGE_JPEG_VALUE, "image".getBytes());
    MockMultipartFile invalidContent =
        new MockMultipartFile("images", "image.gif", MediaType.IMAGE_GIF_VALUE, "image".getBytes());
    boolean validWithValidContent = ImageUtil.isValidAttachmentContent(validContent);
    boolean validWithInvalidContent = ImageUtil.isValidAttachmentContent(invalidContent);
    assertThat(validWithValidContent).isTrue();
    assertThat(validWithInvalidContent).isFalse();
  }

}
