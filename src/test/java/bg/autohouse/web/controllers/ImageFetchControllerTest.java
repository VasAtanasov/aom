package bg.autohouse.web.controllers;

import static bg.autohouse.config.WebConfiguration.APP_V1_MEDIA_TYPE_JSON;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import bg.autohouse.MvcPerformer;
import bg.autohouse.data.models.media.MediaFile;
import bg.autohouse.data.models.media.MediaFunction;
import bg.autohouse.service.services.MediaFileService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import javax.imageio.ImageIO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@AutoConfigureMockMvc
@ActiveProfiles("test")
@TestPropertySource("classpath:test.properties")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class ImageFetchControllerTest extends MvcPerformer {
  static final String API_BASE = "/api/images";

  static final String FILE_KEY =
      "offer-images/2020/05/02/44a8da7d-e55c-467b-ba91-201db3747c2b/image";

  @Autowired MockMvc mockMvc;

  @MockBean MediaFileService mediaFileService;

  @Override
  public MockMvc getMockMvc() {
    return mockMvc;
  }

  @BeforeEach
  void initHeaders() throws Exception, JsonMappingException, JsonProcessingException {
    byte[] imageBytes = extractBytes("00002.jpg");
    when(mediaFileService.getBytes(any(MediaFile.class))).thenReturn(imageBytes);
    MediaFile mediaFile = new MediaFile();
    mediaFile.setOriginalFilename("image");
    mediaFile.setContentType(MediaType.IMAGE_JPEG_VALUE);
    mediaFile.setFileKey(FILE_KEY);
    when(mediaFileService.load(any(MediaFunction.class), any(String.class))).thenReturn(mediaFile);
  }

  @Test
  void when_fetchOfferImage_shouldReturn200() throws Exception {
    mockMvc
        .perform(
            get(API_BASE + "/" + FILE_KEY)
                .contentType(APP_V1_MEDIA_TYPE_JSON)
                .accept(APP_V1_MEDIA_TYPE_JSON))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.IMAGE_JPEG_VALUE));
  }

  @Test
  void when_fetchOfferImageThumbnail_shouldReturn200() throws Exception {
    mockMvc
        .perform(
            get(API_BASE + "/" + FILE_KEY)
                .contentType(APP_V1_MEDIA_TYPE_JSON)
                .accept(APP_V1_MEDIA_TYPE_JSON)
                .param("thumbnail", "true"))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.IMAGE_JPEG_VALUE));
  }

  byte[] extractBytes(String imageName) throws IOException {
    Path sourcePath =
        Paths.get("src", "test", "resources", "bg", "autohouse", "web", "controllers", imageName);
    File imageFile = sourcePath.toFile();
    BufferedImage bufferedImage = ImageIO.read(imageFile);
    try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
      ImageIO.write(bufferedImage, "jpg", baos);
      byte[] imageInByte = baos.toByteArray();
      return imageInByte;
    }
  }
}
