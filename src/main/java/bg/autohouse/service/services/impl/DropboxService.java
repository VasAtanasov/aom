package bg.autohouse.service.services.impl;

import bg.autohouse.data.models.media.MediaFile;
import bg.autohouse.data.models.media.MediaFunction;
import bg.autohouse.data.models.media.StorageType;
import bg.autohouse.service.services.StorageService;
import com.dropbox.core.DbxDownloader;
import com.dropbox.core.DbxException;
import com.dropbox.core.util.IOUtil.ProgressListener;
import com.dropbox.core.v2.DbxClientV2;
import com.dropbox.core.v2.files.FileMetadata;
import com.dropbox.core.v2.files.UploadBuilder;
import com.dropbox.core.v2.files.WriteMode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

@Slf4j
@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class DropboxService implements StorageService {

  @Value("${app.file.storage.dropbox.folder.base}")
  private String storageBaseFolder;

  private final DbxClientV2 client;

  @Override
  public StorageType getType() {
    return StorageType.DROPBOX_BUCKET;
  }

  @Override
  public boolean isConfigured() {
    return client != null;
  }

  @Override
  public void storeFile(MediaFunction mediaFunction, MediaFile record, InputStream inputStream)
      throws IOException {
    String dropboxPath = getUploadPath(record);
    log.info(
        "storing a media file in bucket {}, with key {}", record.getBucket(), record.getFileKey());
    try (inputStream) {
      ProgressListener progressListener = l -> printProgress(l, record.getSize());
      UploadBuilder upload =
              client.files().uploadBuilder(dropboxPath).withMode(WriteMode.OVERWRITE);
      FileMetadata metadata = upload.uploadAndFinish(inputStream, progressListener);
      System.out.println(metadata.toStringMultiline());
      record.setContentHash(metadata.getContentHash());
      record.setSize(metadata.getSize());
      log.info("upload meta data =====> {}", metadata.toString());
    } catch (DbxException ex) {
      log.error("Error uploading to Dropbox: " + ex.getMessage());
    } catch (IOException ex) {
      log.error(
              "Error reading from file \"" + record.getOriginalFilename() + "\": " + ex.getMessage());
    }
  }

  @Override
  public void retrieveFile(MediaFile record, OutputStream outputStream) throws IOException {
    String dropboxPath = getUploadPath(record);
    log.debug("Going to download file" + dropboxPath);
    try {
      ProgressListener progressListener = l -> printProgress(l, record.getSize());
      DbxDownloader<FileMetadata> downloader = client.files().download(dropboxPath);
      FileMetadata download = downloader.download(outputStream, progressListener);
      log.info("Metadata: " + download.toString());
    } catch (DbxException | IOException ex) {
      log.error("Error downloading form Dropbox: " + ex.getMessage());
    }
  }

  @Override
  public void removeFromStorage(MediaFile record) {
    String filePath = getUploadPath(record);
    try {
      client.files().deleteV2(filePath);
    } catch (DbxException e) {
      log.error("File deletion failed.", e);
    }
  }

  private String getUploadPath(MediaFile record) {
    return "/" + storageBaseFolder + "/" + record.getBucket() + "/" + record.getFileKey();
  }

  private static void printProgress(long uploaded, long size) {
    System.out.printf(
        "Uploaded %12d / %12d bytes (%5.2f%%)\n", uploaded, size, 100 * (uploaded / (double) size));
  }
}
