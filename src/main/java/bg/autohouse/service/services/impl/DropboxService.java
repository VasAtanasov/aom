package bg.autohouse.service.services.impl;

import bg.autohouse.service.services.CloudService;
import com.dropbox.core.DbxException;
import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.v2.DbxClientV2;
import com.dropbox.core.v2.files.FileMetadata;
import com.dropbox.core.v2.sharing.SharedLinkMetadata;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
public class DropboxService implements CloudService {

    private static final String RAW_TYPE_POSTFIX = "&raw=1";

    private final DbxClientV2 client;

    @Autowired
    public DropboxService(@Value("${app.dropbox.access.token}") String ACCESS_TOKEN) {
        DbxRequestConfig config = DbxRequestConfig.newBuilder("dropbox/autohouse").build();
        client = new DbxClientV2(config, ACCESS_TOKEN);
    }

    @Override
    public Map<String, Object> uploadFileAndGetParams(MultipartFile file) {
        String imagePath = "/" + file.getOriginalFilename();
        uploadFile(file);
        log.info("File upload successful.");

        SharedLinkMetadata sharedLinkWithSettings = createSharedLinkFromPath(imagePath);
        return new HashMap<>() {{
            put("url", sharedLinkWithSettings.getUrl() + RAW_TYPE_POSTFIX);
            put("contentType", file.getContentType());
        }};
    }

    @Override
    public Map<String, Object> updateFile(MultipartFile newFile, String oldFileId) {
        removeFile(oldFileId);
        return uploadFileAndGetParams(newFile);
    }

    @Override
    public boolean removeFile(String fileId) {
        SharedLinkMetadata sharedLinkMetadata;
        try {
            sharedLinkMetadata = getSharedLinkMetadata(fileId);

            if (sharedLinkMetadata == null) return false;
            client.files().deleteV2(sharedLinkMetadata.getPathLower());
            return true;
        } catch (DbxException e) {
            log.error("File deletion failed.");
            e.printStackTrace();
        }
        return false;
    }

    private FileMetadata uploadFile(MultipartFile file) {
        InputStream in;
        try {
            in = file.getInputStream();
            return client.files().uploadBuilder("/" + file.getOriginalFilename()).uploadAndFinish(in);
        } catch (IOException | DbxException e) {
            log.error("File upload failed.");
            e.printStackTrace();
        }
        return null;
    }

    private SharedLinkMetadata createSharedLinkFromPath(String path) {
        try {
            return client.sharing().createSharedLinkWithSettings(path);
        } catch (DbxException e) {
            log.error("Creating shared link failed.");
            e.printStackTrace();
        }
        return null;
    }

    private SharedLinkMetadata getSharedLinkMetadata(String url) {
        try {
            return client.sharing().getSharedLinkMetadata(url);
        } catch (DbxException e) {
            log.error("Retrieving shared link failed.");
            e.printStackTrace();
        }
        return null;
    }
}
