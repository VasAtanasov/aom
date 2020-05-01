package bg.autohouse.service.services.impl;

import bg.autohouse.data.models.media.MediaFile;
import bg.autohouse.data.models.media.MediaFunction;
import bg.autohouse.data.models.media.StorageType;
import bg.autohouse.data.repositories.MedialFileRepository;
import bg.autohouse.service.services.MediaFileService;
import bg.autohouse.service.services.StorageService;
import bg.autohouse.util.Assert;
import com.google.common.collect.Ordering;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Supplier;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class MediaFileServiceImpl implements MediaFileService {
  private final MedialFileRepository medialFileRepository;
  private final List<StorageService> storages;

  private static Supplier<IllegalStateException> notConfigured(final StorageType storageType) {
    return () -> new IllegalStateException("Storage is not configured: " + storageType);
  }

  private Optional<StorageService> findStorage(final StorageType storageType) {
    return storages.stream()
        .filter(fss -> fss.getType() == storageType && fss.isConfigured())
        .findAny();
  }

  private StorageService getStorage(final StorageType storageType) {
    return findStorage(storageType).orElseThrow(notConfigured(storageType));
  }

  private StorageService getStorageOrFallback(final StorageType preferredType) {
    return findStorage(preferredType)
        .orElseGet(
            () -> {
              final Ordering<StorageService> explicit =
                  Ordering.explicit(
                          StorageType.DROPBOX_BUCKET,
                          StorageType.LOCAL_FOLDER,
                          StorageType.LOCAL_DATABASE)
                      .onResultOf(StorageService::getType);

              return storages.stream()
                  .filter(StorageService::isConfigured)
                  .sorted(explicit)
                  .findFirst()
                  .orElseThrow(notConfigured(preferredType));
            });
  }

  @Override
  @Transactional(readOnly = true)
  public MediaFile load(UUID id) {
    return medialFileRepository.getOne(id);
  }

  @Override
  @Transactional(readOnly = true)
  public MediaFile load(MediaFunction function, String imageKey) {
    return medialFileRepository.findByBucketAndFileKey(function.resolveBucketName(), imageKey);
  }

  @Override
  @Transactional(readOnly = true)
  public boolean doesFileExist(MediaFunction function, String imageKey) {
    return medialFileRepository.existsByBucketAndFileKey(function.resolveBucketName(), imageKey);
  }

  @Override
  @Transactional(rollbackFor = IOException.class)
  public UUID storeFile(
      MultipartFile file,
      String fileKey,
      MediaFunction function,
      String contentType,
      String originalFilename,
      UUID referenceId) {
    final StorageService storage = getStorageOrFallback(function.storageType());
    Assert.notNull(function, "Unspecified media function.");
    String bucket = function.resolveBucketName();
    log.info(
        "storing a file, with function {}, bucket {}, file key {}, content type: {},  original name: {}",
        function,
        bucket,
        fileKey,
        contentType,
        originalFilename);
    MediaFile record = medialFileRepository.findByBucketAndFileKey(bucket, fileKey);
    if (Assert.isEmpty(record)) {
      record =
          MediaFile.builder()
              .bucket(bucket)
              .storageType(function.storageType())
              .contentType(contentType)
              .size(file.getSize())
              .fileKey(fileKey)
              .originalFilename(originalFilename)
              .referenceId(referenceId)
              .build();
    }
    try (final InputStream fis = file.getInputStream();
        final BufferedInputStream bis = new BufferedInputStream(fis)) {
      record = medialFileRepository.save(record);
      storage.storeFile(function, record, bis);
      log.info("media record stored and has mime type");
      return record.getId();
    } catch (IOException e) {
      log.error("Error storing media file, returning null", e);
      return null;
    }
  }

  @Override
  @Transactional(rollbackFor = IOException.class)
  public UUID storeFile(
      byte[] file,
      String fileKey,
      MediaFunction function,
      String contentType,
      String originalFilename,
      UUID referenceId) {
    final StorageService storage = getStorageOrFallback(function.storageType());
    Assert.notNull(function, "Unspecified media function.");
    String bucket = function.resolveBucketName();
    log.info(
        "storing a file, with function {}, bucket {}, file key {}, content type: {},  original name: {}",
        function,
        bucket,
        fileKey,
        contentType,
        originalFilename);
    MediaFile record = medialFileRepository.findByBucketAndFileKey(bucket, fileKey);
    if (Assert.isEmpty(record)) {
      record =
          MediaFile.builder()
              .bucket(bucket)
              .storageType(function.storageType())
              .contentType(contentType)
              .size(file.length)
              .fileKey(fileKey)
              .originalFilename(originalFilename)
              .referenceId(referenceId)
              .build();
    }
    try (final InputStream fis = new ByteArrayInputStream(file)) {
      record = medialFileRepository.save(record);
      storage.storeFile(function, record, fis);
      log.info("media record stored and has mime type");
      return record.getId();
    } catch (IOException e) {
      log.error("Error storing media file, returning null", e);
      return null;
    }
  }

  @Override
  @Transactional(readOnly = true, rollbackFor = IOException.class)
  public byte[] getBytes(final UUID uuid) throws IOException {
    final MediaFile mediaFile = medialFileRepository.getOne(uuid);
    final StorageService storage = getStorage(mediaFile.getStorageType());
    try (final ByteArrayOutputStream bos = new ByteArrayOutputStream()) {
      storage.retrieveFile(mediaFile, bos);
      return bos.toByteArray();
    }
  }

  @Override
  @Transactional(readOnly = true, rollbackFor = IOException.class)
  public void downloadTo(final UUID uuid, final Path targetPath) throws IOException {
    final MediaFile mediaFile = medialFileRepository.getOne(uuid);
    final StorageService storage = getStorage(mediaFile.getStorageType());
    try (final FileOutputStream fos = new FileOutputStream(targetPath.toFile());
        final BufferedOutputStream bos = new BufferedOutputStream(fos)) {
      storage.retrieveFile(mediaFile, bos);
    }
  }

  @Override
  @Transactional
  public void remove(final UUID uuid) {
    final MediaFile mediaFile = medialFileRepository.getOne(uuid);
    getStorage(mediaFile.getStorageType()).removeFromStorage(mediaFile);
    medialFileRepository.delete(mediaFile);
  }
}
