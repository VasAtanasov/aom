package bg.autohouse.service.services;

import bg.autohouse.data.models.media.MediaFile;
import bg.autohouse.data.models.media.MediaFunction;
import bg.autohouse.data.models.media.StorageType;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public interface StorageService {
  StorageType getType();

  boolean isConfigured();

  void storeFile(MediaFunction mediaFunction, MediaFile mediaFile, InputStream inputStream)
      throws IOException;

  void retrieveFile(MediaFile record, OutputStream outputStream) throws IOException;

  void removeFromStorage(MediaFile record);
}
