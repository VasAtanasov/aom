package bg.autohouse.spider.cache;

import static bg.autohouse.spider.constants.Constant.TEMP_STORAGE_PATH;

import bg.autohouse.spider.domain.dto.cg.CarsTrimsWrapper;
import bg.autohouse.spider.domain.dto.cg.EnginesList;
import bg.autohouse.spider.domain.dto.cg.MakersModelsWrapper;
import bg.autohouse.spider.domain.dto.cg.ModelsCarsWrapper;
import bg.autohouse.spider.domain.dto.cg.OptionsWrapper;
import bg.autohouse.spider.domain.dto.cg.TransmissionWrapper;
import bg.autohouse.spider.service.AppConfigurationService;
import com.google.common.base.Preconditions;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.apache.commons.lang3.StringUtils;
import org.ehcache.Cache;
import org.ehcache.PersistentCacheManager;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.CacheManagerBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.ehcache.config.units.MemoryUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SpiderCacheManager implements AutoCloseable {
  private static final Logger LOG = LoggerFactory.getLogger(SpiderCacheManager.class);
  private static final String PREFIX_CACHE = "auto_";
  private static final String CACHE_FOLDER_NAME = "autohouse_data";

  private final PersistentCacheManager cacheManager;
  private Path storageBasePath;

  public SpiderCacheManager() {
    setStorageBasePath();
    this.cacheManager =
        CacheManagerBuilder.newCacheManagerBuilder()
            .with(
                CacheManagerBuilder.persistence(
                    new File(storageBasePath.toString(), CACHE_FOLDER_NAME)))
            .withCache(
                buildName("makers"),
                CacheConfigurationBuilder.newCacheConfigurationBuilder(
                    String.class,
                    MakersModelsWrapper.class,
                    ResourcePoolsBuilder.newResourcePoolsBuilder().disk(2, MemoryUnit.MB, true)))
            .withCache(
                buildName("maker_models"),
                CacheConfigurationBuilder.newCacheConfigurationBuilder(
                    String.class,
                    ModelsCarsWrapper.class,
                    ResourcePoolsBuilder.newResourcePoolsBuilder().disk(10, MemoryUnit.MB, true)))
            .withCache(
                buildName("cars_trims"),
                CacheConfigurationBuilder.newCacheConfigurationBuilder(
                    String.class,
                    CarsTrimsWrapper.class,
                    ResourcePoolsBuilder.newResourcePoolsBuilder().disk(10, MemoryUnit.MB, true)))
            .withCache(
                buildName("transmission"),
                CacheConfigurationBuilder.newCacheConfigurationBuilder(
                    String.class,
                    TransmissionWrapper.class,
                    ResourcePoolsBuilder.newResourcePoolsBuilder().disk(10, MemoryUnit.MB, true)))
            .withCache(
                buildName("engines"),
                CacheConfigurationBuilder.newCacheConfigurationBuilder(
                    String.class,
                    EnginesList.class,
                    ResourcePoolsBuilder.newResourcePoolsBuilder().disk(10, MemoryUnit.MB, true)))
            .withCache(
                buildName("options"),
                CacheConfigurationBuilder.newCacheConfigurationBuilder(
                    String.class,
                    OptionsWrapper.class,
                    ResourcePoolsBuilder.newResourcePoolsBuilder().disk(20, MemoryUnit.MB, true)))
            .build(true);
  }

  @Override
  public void close() {
    cacheManager.close();
  }

  public Cache<String, ModelsCarsWrapper> makerModelsCache() {
    return this.cacheManager.getCache(
        buildName("maker_models"), String.class, ModelsCarsWrapper.class);
  }

  public Cache<String, MakersModelsWrapper> makersCache() {
    return this.cacheManager.getCache(buildName("makers"), String.class, MakersModelsWrapper.class);
  }

  public Cache<String, CarsTrimsWrapper> carsTrimsCache() {
    return this.cacheManager.getCache(
        buildName("cars_trims"), String.class, CarsTrimsWrapper.class);
  }

  public Cache<String, TransmissionWrapper> transmissionsCache() {
    return this.cacheManager.getCache(
        buildName("transmission"), String.class, TransmissionWrapper.class);
  }

  public Cache<String, EnginesList> enginesCache() {
    return this.cacheManager.getCache(buildName("engines"), String.class, EnginesList.class);
  }

  public Cache<String, OptionsWrapper> optionsCache() {
    return this.cacheManager.getCache(buildName("options"), String.class, OptionsWrapper.class);
  }

  private void setStorageBasePath() {
    try {
      final String storageFolderPath = AppConfigurationService.storageBasePath();

      if (StringUtils.isNotBlank(storageFolderPath)) {
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

  private String buildName(String name) {
    return (PREFIX_CACHE + name).toLowerCase();
  }

  private static void checkDirectoryExistsAndWritable(final Path directory) throws IOException {
    final File file = directory.toFile();

    if (!file.exists()) {
      Files.createDirectory(directory);
    } else {
      Preconditions.checkState(file.isDirectory(), "Storage path is not directory");
      Preconditions.checkState(file.canWrite(), "Storage path is not writable");
    }
  }
}
