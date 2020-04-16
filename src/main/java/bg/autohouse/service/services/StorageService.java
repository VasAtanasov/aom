package bg.autohouse.service.services;

import bg.autohouse.data.models.media.MediaFile;
import bg.autohouse.data.models.media.MediaFunction;
import bg.autohouse.data.models.media.StorageType;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.springframework.web.multipart.MultipartFile;

public interface StorageService {

  boolean storeMedia(MediaFile record, MultipartFile file);

  void storeFile(MediaFunction mediaFunction, MediaFile mediaFile, InputStream inputStream)
      throws IOException;

  byte[] downloadMedia(MediaFile record);

  void retrieveFile(MediaFile record, OutputStream outputStream) throws IOException;

  boolean removeMedia(MediaFile record);

  void removeFromStorage(MediaFile record);

  boolean isConfigured();

  StorageType getType();
}
