package bg.autohouse.service.services.impl;

import bg.autohouse.data.models.PersistentFileContent;
import bg.autohouse.data.models.media.MediaFile;
import bg.autohouse.data.models.media.MediaFunction;
import bg.autohouse.data.models.media.StorageType;
import bg.autohouse.data.repositories.PersistentFileContentRepository;
import bg.autohouse.service.services.StorageService;
import com.google.common.io.ByteStreams;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class DatabaseFileStorageService implements StorageService {

  private final PersistentFileContentRepository fileRepository;

  @Override
  public StorageType getType() {
    return StorageType.LOCAL_DATABASE;
  }

  @Override
  public boolean isConfigured() {
    return true;
  }

  @Override
  @Transactional
  public void storeFile(MediaFunction mediaFunction, MediaFile mediaFile, InputStream inputStream)
      throws IOException {
    byte[] content = ByteStreams.toByteArray(inputStream);
    PersistentFileContent fileContent = PersistentFileContent.of(mediaFile, content);
    fileRepository.save(fileContent);
  }

  @Override
  @Transactional(readOnly = true)
  public void retrieveFile(MediaFile record, OutputStream outputStream) throws IOException {
    PersistentFileContent fileContent = fileRepository.getOne(record.getId());
    try (InputStream inputStream = new ByteArrayInputStream(fileContent.getContent())) {
      ByteStreams.copy(inputStream, outputStream);
    }
  }

  @Override
  @Transactional
  public void removeFromStorage(MediaFile record) {
    PersistentFileContent fileContent = fileRepository.getOne(record.getId());
    fileRepository.delete(fileContent);
  }
}
