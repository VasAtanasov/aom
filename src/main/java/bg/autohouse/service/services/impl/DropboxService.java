package bg.autohouse.service.services.impl;

import bg.autohouse.data.models.media.MediaFile;
import bg.autohouse.service.services.CloudService;
import com.dropbox.core.DbxException;
import com.dropbox.core.util.IOUtil.ProgressListener;
import com.dropbox.core.v2.DbxClientV2;
import com.dropbox.core.v2.files.FileMetadata;
import com.dropbox.core.v2.files.UploadErrorException;
import com.dropbox.core.v2.files.WriteMode;
import com.dropbox.core.v2.sharing.SharedLinkMetadata;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class DropboxService implements CloudService {

  private static final String RAW_TYPE_POSTFIX = "&raw=1";

  private final DbxClientV2 client;

  @Override
  public boolean storeMedia(MediaFile record, MultipartFile file) {
    String filePath = "/" + record.getBucket() + "/" + record.getKey();
    log.info("storing a media file in bucket {}, with key {}", record.getBucket(), record.getKey());
    FileMetadata metadata = uploadFile(file, filePath);
    return metadata != null;
  }

  @Override
  public Map<String, Object> uploadFileAndGetParams(MultipartFile file) {
    String imagePath = "/" + file.getOriginalFilename();
    uploadFile(file, imagePath);
    log.info("File upload successful.");

    SharedLinkMetadata sharedLinkWithSettings = createSharedLinkFromPath(imagePath);
    Map<String, Object> params = new HashMap<>();
    params.put("url", sharedLinkWithSettings.getUrl() + RAW_TYPE_POSTFIX);
    params.put("contentType", file.getContentType());
    return params;
  }

  @Override
  public Map<String, Object> updateFile(MultipartFile newFile, String oldFileId) {
    removeFile(oldFileId);
    return uploadFileAndGetParams(newFile);
  }

  /**
   * Uploads a file in a single request. This approach is preferred for small files since it
   * eliminates unnecessary round-trips to the servers.
   *
   * @param localFIle local file to upload
   * @param dropboxPath Where to upload the file to within Dropbox
   */
  private FileMetadata uploadFile(MultipartFile file, String dropboxPath) {
    try (InputStream in = file.getInputStream()) {
      ProgressListener progressListener = l -> printProgress(l, file.getSize());

      FileMetadata metadata =
          client
              .files()
              .uploadBuilder(dropboxPath)
              .withMode(WriteMode.OVERWRITE)
              .uploadAndFinish(in, progressListener);

      System.out.println(metadata.toStringMultiline());
      return metadata;
    } catch (UploadErrorException ex) {
      log.error("Error uploading to Dropbox: " + ex.getMessage());
    } catch (DbxException ex) {
      log.error("Error uploading to Dropbox: " + ex.getMessage());
    } catch (IOException ex) {
      log.error(
          "Error reading from file \"" + file.getOriginalFilename() + "\": " + ex.getMessage());
    }
    return null;
  }

  private static void printProgress(long uploaded, long size) {
    System.out.printf(
        "Uploaded %12d / %12d bytes (%5.2f%%)\n", uploaded, size, 100 * (uploaded / (double) size));
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
