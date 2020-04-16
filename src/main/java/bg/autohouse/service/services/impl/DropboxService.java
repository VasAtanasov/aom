package bg.autohouse.service.services.impl;

import bg.autohouse.data.models.media.MediaFile;
import bg.autohouse.service.services.StorageService;
import com.dropbox.core.DbxDownloader;
import com.dropbox.core.DbxException;
import com.dropbox.core.util.IOUtil.ProgressListener;
import com.dropbox.core.v2.DbxClientV2;
import com.dropbox.core.v2.files.FileMetadata;
import com.dropbox.core.v2.files.UploadErrorException;
import com.dropbox.core.v2.files.WriteMode;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class DropboxService implements StorageService {

  private final DbxClientV2 client;

  @Override
  public boolean storeMedia(MediaFile record, MultipartFile file) {
    String filePath = getFilePath(record);
    log.info(
        "storing a media file in bucket {}, with key {}", record.getBucket(), record.getFileKey());
    FileMetadata metadata = uploadFile(file, filePath);

    if (metadata != null) {
      record.setRev(metadata.getRev());
      record.setContentHash(metadata.getContentHash());
      record.setSize(metadata.getSize());
    }

    return metadata != null;
  }

  @Override
  public byte[] downloadMedia(MediaFile record) {
    String filePath = getFilePath(record);
    log.debug("Going to download file" + filePath);
    try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
      DbxDownloader<FileMetadata> downloader = client.files().download(filePath);
      FileMetadata download = downloader.download(outputStream);
      log.info("Metadata: " + download.toString());
      byte[] out = outputStream.toByteArray();
      return out;
    } catch (DbxException | IOException ex) {
      log.error("Error downloading form Dropbox: " + ex.getMessage());
    }
    return null;
  }

  @Override
  public boolean removeMedia(MediaFile record) {
    String filePath = getFilePath(record);
    try {
      client.files().deleteV2(filePath);
      return true;
    } catch (DbxException e) {
      log.error("File deletion failed.");
      e.printStackTrace();
    }
    return false;
  }

  private String getFilePath(MediaFile record) {
    return "/" + record.getBucket() + "/" + record.getFileKey();
  }

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
      log.info("upload meta data =====> {}", metadata.toString());
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
}
