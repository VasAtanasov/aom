package bg.autohouse.service.services.impl;

import bg.autohouse.data.models.media.MediaFile;
import bg.autohouse.data.models.media.MediaFunction;
import bg.autohouse.data.models.media.StorageType;
import bg.autohouse.service.services.StorageService;
import bg.autohouse.util.Assert;
import com.google.common.base.Preconditions;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import javax.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class LocalFolderStorageService implements StorageService {

  private Path storageBasePath;

  @Value("${file.storage.folder}")
  @PostConstruct
  public void init(String path) throws IOException {

    if (Assert.has(path)) {
      log.info("File local storage folder: {}", path);

      this.storageBasePath = Paths.get(path);
    } else {
      log.warn("Using temporary folder");
      this.storageBasePath = Files.createTempDirectory("autohouse").toAbsolutePath();
      return;
    }

    checkDirectoryExistsAndWritable(this.storageBasePath);
  }

  @Override
  public StorageType getType() {
    return StorageType.LOCAL_FOLDER;
  }

  @Override
  public boolean isConfigured() {
    return storageBasePath != null;
  }

  @Override
  public void storeFile(MediaFunction mediaFunction, MediaFile mediaFile, InputStream inputStream)
      throws IOException {

    final Path storageFile = this.storageBasePath.resolve(mediaFunction.formatFilename(mediaFile));
    mediaFile.setResourceUrl(storageFile.toUri().toURL());

    try {
      Files.copy(inputStream, storageFile, StandardCopyOption.REPLACE_EXISTING);
    } finally {
      inputStream.close();
    }
  }

  @Override
  public void retrieveFile(MediaFile record, OutputStream outputStream) throws IOException {
    Files.copy(getFileStoragePath(record), outputStream);
  }

  @Override
  public void removeFromStorage(MediaFile record) {
    final Path storageFile = getFileStoragePath(record);
    try {
      Files.deleteIfExists(storageFile);
    } catch (final IOException e) {
      log.warn("Could not delete missing file: {}.", storageFile);
      throw new RuntimeException(e);
    }
  }

  private static void checkDirectoryExistsAndWritable(final Path directory) throws IOException {
    final File file = directory.toFile();

    if (!file.exists()) {
      Files.createDirectory(directory);
    } else {
      Preconditions.checkState(file.isDirectory(), "Storage path is not directory");
      Preconditions.checkState(file.canWrite(), "Storage path is not writable");
    }
  }

  private static Path getFileStoragePath(final MediaFile mediaFile) {
    try {
      return Paths.get(mediaFile.getResourceUrl().toURI());
    } catch (URISyntaxException ex) {
      throw new RuntimeException("Could not parse resourceUrl as File", ex);
    }
  }
}
