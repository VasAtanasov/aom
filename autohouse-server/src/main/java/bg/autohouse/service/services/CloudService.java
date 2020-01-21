package bg.autohouse.service.services;

import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

public interface CloudService {

    Map<String, Object> uploadFileAndGetParams(MultipartFile file);

    Map<String, Object> updateFile(MultipartFile newFile, String oldFileId);

    boolean removeFile(String fileId);
}
