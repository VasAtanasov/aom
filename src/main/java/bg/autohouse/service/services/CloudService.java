package bg.autohouse.service.services;

import java.util.Map;
import org.springframework.web.multipart.MultipartFile;

public interface CloudService {

  Map<String, Object> uploadFileAndGetParams(MultipartFile file);

  Map<String, Object> updateFile(MultipartFile newFile, String oldFileId);

  boolean removeFile(String fileId);
}
