package bg.autohouse.service.services;

import bg.autohouse.data.models.media.MediaFile;
import org.springframework.web.multipart.MultipartFile;

public interface StorageService {

  boolean storeMedia(MediaFile record, MultipartFile file);

  byte[] downloadMedia(MediaFile record);

  boolean removeMedia(MediaFile record);
}
