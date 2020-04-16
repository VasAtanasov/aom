package bg.autohouse.service.services;

import bg.autohouse.data.models.media.MediaFile;
import java.util.Map;
import org.springframework.web.multipart.MultipartFile;

public interface CloudService {

  Map<String, Object> uploadFileAndGetParams(MultipartFile file);

  Map<String, Object> updateFile(MultipartFile newFile, String oldFileId);

  boolean removeFile(String fileId);

  boolean storeMedia(MediaFile record, MultipartFile file);
}
