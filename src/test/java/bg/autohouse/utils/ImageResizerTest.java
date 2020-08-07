package bg.autohouse.utils;

import bg.autohouse.util.ImageResizer;
import bg.autohouse.util.ImageUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import static bg.autohouse.web.controllers.ImageFetchControllerTest.extractBytes;

public class ImageResizerTest {

  ImageResizer imageResizer;

  @BeforeEach
  void init() {
    imageResizer = new ImageResizer();
  }

  @Test
  void when_resizeFromByteArray_shouldReturn() throws IOException {
    byte[] imageBytes = extractBytes("00002.jpg");
    byte[] resizedImage = imageResizer.resize(imageBytes, 50, 50, true);
    BufferedImage inputImage = toBufferImage(resizedImage);
    assertThat(inputImage.getWidth()).isEqualTo(50);
  }

  @Test
  void when_resizeAndCropCenter_shouldReturn() throws IOException {
    byte[] imageBytes = extractBytes("00002.jpg");
    byte[] resizedImage = imageResizer.resize(imageBytes, 50, 50, false);
    BufferedImage inputImage = toBufferImage(resizedImage);
    assertThat(inputImage.getWidth()).isEqualTo(50);
  }

  @Test
  void when_toJpgDownscaleToSize_shouldReturn() throws IOException {
    byte[] imageBytes = extractBytes("00002.jpg");
    InputStream imageStream = byteToByteArrayInputStream(imageBytes);
    byte[] image = imageResizer.toJpgDownscaleToSize(imageStream);
    assertThat(image).isNotEmpty();
  }

  @Test
  void when_createThumbnail_inputStream_shouldReturn() throws IOException {
    byte[] imageBytes = extractBytes("00002.jpg");
    InputStream imageStream = byteToByteArrayInputStream(imageBytes);
    byte[] thumbnail = imageResizer.createThumbnail(imageStream, 50, 50);
    BufferedImage inputImage = toBufferImage(thumbnail);
    assertThat(inputImage.getWidth()).isEqualTo(50);
  }

  public static ByteArrayInputStream byteToByteArrayInputStream(byte[] bytes) throws IOException {
    try (ByteArrayInputStream inputStream = new ByteArrayInputStream(bytes)) {
      return inputStream;
    }
  }

  public static BufferedImage toBufferImage(byte[] imageBytes) throws IOException {
    try (ByteArrayInputStream imageStream = byteToByteArrayInputStream(imageBytes)) {
      return ImageIO.read(imageStream);
    }
  }
}
