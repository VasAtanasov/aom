package bg.autohouse.spider.cache;

import bg.autohouse.spider.domain.dto.cg.MakersModelsWrapper;
import bg.autohouse.spider.domain.dto.cg.ModelsCarsWrapper;
import java.io.File;
import org.ehcache.Cache;
import org.ehcache.PersistentCacheManager;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.CacheManagerBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.ehcache.config.units.MemoryUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SpiderCacheManager implements AutoCloseable {
  private static final Logger LOG = LoggerFactory.getLogger("ApplicationCacheManager");
  private static final String PREFIX_CACHE = "auto_";
  private final PersistentCacheManager cacheManager;

  public SpiderCacheManager() {
    String storagePath = "cached_data";
    this.cacheManager =
        CacheManagerBuilder.newCacheManagerBuilder()
            .with(CacheManagerBuilder.persistence(new File(storagePath, "autohouse_data")))
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
            .build(true);
  }

  public Cache<String, ModelsCarsWrapper> makerModelsCache() {
    return this.cacheManager.getCache(
        buildName("maker_models"), String.class, ModelsCarsWrapper.class);
  }

  public Cache<String, MakersModelsWrapper> makersCache() {
    return this.cacheManager.getCache(buildName("makers"), String.class, MakersModelsWrapper.class);
  }

  private String buildName(String name) {
    return (PREFIX_CACHE + name).toLowerCase();
  }

  @Override
  public void close() {
    cacheManager.close();
  }
}
