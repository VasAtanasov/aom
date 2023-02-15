package com.github.vaatech.aom.util.cache.adaptors.ehcache;

import com.github.vaatech.aom.util.cache.CacheProviderType;
import org.apache.commons.lang3.StringUtils;
import org.ehcache.Cache;
import org.ehcache.CacheManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class EhcacheDiskCacheAdapter extends BaseEhcacheCacheAdapter {

  public static final String TEMP_STORAGE_PATH = "storage";
  private static final Logger LOG = LoggerFactory.getLogger(EhcacheDiskCacheAdapter.class);

  private Path storageBasePath;

  public EhcacheDiskCacheAdapter(String storagePath) {
    setStorageBasePath(storagePath);
  }

  private static void checkDirectoryExistsAndWritable(final Path directory) throws IOException {
    final File file = directory.toFile();
    if (!file.exists()) {
      Files.createDirectory(directory);
    } else {
      if (!file.isDirectory()) {
        throw new IllegalStateException("Storage path is not directory");
      }
      if (!file.canWrite()) {
        throw new IllegalStateException("Storage path is not writable");
      }
    }
  }

  @Override
  protected CacheManager cacheManagerProvider() {
    return EhcacheCacheMangerFactory.disk(storageBasePath);
  }

  @Override
  protected <T extends Serializable> Cache<String, T> cacheProvider(
      String alias, Long expireMillis, Long size, Class<T> resultType, CacheManager manager) {
    return EhcacheCacheFactory.disk(alias, expireMillis, size, resultType, manager);
  }

  @Override
  public CacheProviderType getType() {
    return CacheProviderType.EHCACHE_DISK;
  }

  private void setStorageBasePath(String storageFolderPath) {
    try {
      if (StringUtils.isNotEmpty(storageFolderPath) && StringUtils.isNotBlank(storageFolderPath)) {
        LOG.info("File local storage folder: {}", storageFolderPath);

        this.storageBasePath = Paths.get(storageFolderPath);
      } else {
        LOG.warn("Property \"file.storage.folder\" note set. Using temporary folder");
        this.storageBasePath = Files.createTempDirectory(TEMP_STORAGE_PATH).toAbsolutePath();
      }

      checkDirectoryExistsAndWritable(this.storageBasePath);
    } catch (IOException io) {
      LOG.error("File storage path is not configured!");
    }
  }
}
