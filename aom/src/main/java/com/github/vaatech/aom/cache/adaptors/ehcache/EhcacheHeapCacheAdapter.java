package com.github.vaatech.aom.cache.adaptors.ehcache;

import com.github.vaatech.aom.cache.CacheProviderType;
import org.ehcache.Cache;
import org.ehcache.CacheManager;

import java.io.Serializable;

public class EhcacheHeapCacheAdapter extends BaseEhcacheCacheAdapter {

  @Override
  protected <T extends Serializable> Cache<String, T> cacheProvider(
      final String alias,
      final Long expireMillis,
      final Long size,
      final Class<T> resultType,
      CacheManager manager) {
    return EhcacheCacheFactory.heap(alias, expireMillis, size, resultType, manager);
  }

  @Override
  public CacheProviderType getType() {
    return CacheProviderType.EHCACHE_HEAP;
  }

  @Override
  protected CacheManager cacheManagerProvider() {
    return EhcacheCacheMangerFactory.heap();
  }
}
