package bg.autohouse.service.services.impl;

import bg.autohouse.data.models.media.MediaFile;
import bg.autohouse.data.models.media.MediaFunction;
import bg.autohouse.service.services.MediaFileService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
@ActiveProfiles("test")
@TestPropertySource("classpath:test.properties")
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MediaFileServiceImplTest {

  final String FILE_KEY = "test-files";

  @Autowired MediaFileService mediaFileService;

  @Test
  public void testDatabase() {
    testBackend(MediaFunction.USER_PROFILE_IMAGE);
  }

  @Test
  public void testLocalFolder() {
    testBackend(MediaFunction.OFFER_IMAGE);
  }

  private void testBackend(final MediaFunction function) {
    final byte[] data = "Hello world".getBytes(StandardCharsets.UTF_8);
    final UUID referenceId = UUID.randomUUID();
    final String originalFileName = "test.txt";
    final String fileKey = FILE_KEY + "/" + referenceId.toString() + "/" + originalFileName;
    mediaFileService.storeFile(
        data, fileKey, function, MediaType.TEXT_PLAIN_VALUE, originalFileName, referenceId);
    MediaFile mediaFile = mediaFileService.load(function, fileKey);
    checkMetadata(function, mediaFile);
    try {
      final byte[] dbData = mediaFileService.getBytes(mediaFile.getId());
      assertThat(dbData).isEqualTo(data);
    } catch (final IOException ioe) {
      throw new RuntimeException(ioe);
    } finally {
      mediaFileService.remove(mediaFile.getId());
    }
  }

  private static void checkMetadata(final MediaFunction function, MediaFile mediaFile) {
    assertThat(mediaFile.getStorageType()).isEqualTo(function.storageType());
    assertThat(mediaFile.getOriginalFilename()).isEqualTo("test.txt");
    assertThat(mediaFile.getContentType()).isEqualTo("text/plain");
    assertThat(mediaFile.getSize()).isEqualTo(11L);
  }
}
