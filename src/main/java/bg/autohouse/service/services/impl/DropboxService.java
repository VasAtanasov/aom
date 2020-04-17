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
import com.dropbox.core.v2.files.UploadErrorException;
import com.dropbox.core.v2.files.WriteMode;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class DropboxService implements StorageService {

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
    String dropboxPath = getFilePath(record);
    log.info(
        "storing a media file in bucket {}, with key {}", record.getBucket(), record.getFileKey());

    try {
      ProgressListener progressListener = l -> printProgress(l, record.getSize());
      UploadBuilder upload =
          client.files().uploadBuilder(dropboxPath).withMode(WriteMode.OVERWRITE);
      FileMetadata metadata = upload.uploadAndFinish(inputStream, progressListener);
      System.out.println(metadata.toStringMultiline());

      if (metadata != null) {
        record.setContentHash(metadata.getContentHash());
        record.setSize(metadata.getSize());
        record.setResourceUrl(new URL(metadata.getPathLower()));
      }
      log.info("upload meta data =====> {}", metadata.toString());

    } catch (UploadErrorException ex) {
      log.error("Error uploading to Dropbox: " + ex.getMessage());
    } catch (DbxException ex) {
      log.error("Error uploading to Dropbox: " + ex.getMessage());
    } catch (IOException ex) {
      log.error(
          "Error reading from file \"" + record.getOriginalFilename() + "\": " + ex.getMessage());
    } finally {
      inputStream.close();
    }
  }

  @Override
  public void retrieveFile(MediaFile record, OutputStream outputStream) throws IOException {
    String dropboxPath = getFilePath(record);
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
    String filePath = getFilePath(record);
    try {
      client.files().deleteV2(filePath);
    } catch (DbxException e) {
      log.error("File deletion failed.", e);
    }
  }

  private String getFilePath(MediaFile record) {
    return "/" + record.getBucket() + "/" + record.getFileKey();
  }

  private static void printProgress(long uploaded, long size) {
    System.out.printf(
        "Uploaded %12d / %12d bytes (%5.2f%%)\n", uploaded, size, 100 * (uploaded / (double) size));
  }
}
