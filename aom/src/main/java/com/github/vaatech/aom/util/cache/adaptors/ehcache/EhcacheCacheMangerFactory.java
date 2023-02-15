package com.github.vaatech.aom.util.cache.adaptors.ehcache;

import org.ehcache.CacheManager;
import org.ehcache.config.builders.CacheManagerBuilder;

import java.io.File;
import java.nio.file.Path;

class EhcacheCacheMangerFactory {

  static CacheManager heap() {
    return create(ResourceType.HEAP, null);
  }

  static CacheManager disk(Path storagePath) {
    return create(ResourceType.HEAP, storagePath);
  }

  private static CacheManager create(ResourceType type, Path storagePath) {
    return switch (type) {
      case HEAP -> CacheManagerBuilder.newCacheManagerBuilder().build(true);
      case DISK -> {
        if (storagePath == null)
          throw new IllegalArgumentException("Storage path must be specified");
        yield CacheManagerBuilder.newCacheManagerBuilder()
            .with(CacheManagerBuilder.persistence(new File(storagePath.toString())))
            .build(true);
      }
    };
  }

  public static CacheManager create(ResourceType type) {
    return create(type, null);
  }
}
