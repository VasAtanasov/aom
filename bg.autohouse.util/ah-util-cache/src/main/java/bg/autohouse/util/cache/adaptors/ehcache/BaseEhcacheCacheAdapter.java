package bg.autohouse.util.cache.adaptors.ehcache;

import bg.autohouse.util.cache.adaptors.CacheAdapter;
import bg.autohouse.util.common.logging.ApplicationLoggerFactory;
import bg.autohouse.util.common.logging.Logger;
import org.ehcache.Cache;
import org.ehcache.CacheManager;
import org.ehcache.Status;

import java.io.Closeable;
import java.io.Serializable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.function.Supplier;

abstract class BaseEhcacheCacheAdapter implements CacheAdapter, Closeable {

  private static final Logger LOG = ApplicationLoggerFactory.getLogger(BaseEhcacheCacheAdapter.class);
  private static final ConcurrentMap<String, CacheManager> ALL_CACHE_MANAGERS =
      new ConcurrentHashMap<>();

  @Override
  public <T extends Serializable> T computeIfAbsent(
      final String tenantId,
      final String cacheName,
      final Long expireMillis,
      final Long size,
      final String key,
      final Class<T> resultType,
      final Supplier<T> valueSupplier) {

    final Cache<String, T> cache =
        getCacheInternal(tenantId, cacheName, expireMillis, size, resultType);

    LOG.debug(
        String.format(
            "[%s:%s]: Checking EhCache cache for invocation: %s", tenantId, cacheName, key));

    T value = valueSupplier.get();
    cache.put(key, value);

    LOG.debug(
        String.format(
            "[%s:%s]: Value after invocation %s was cached in EhCache successfully",
            tenantId, cacheName, key));

    return value;
  }

  @Override
  public void close() {
    ALL_CACHE_MANAGERS.values().parallelStream().forEach(CacheManager::close);
  }

  @Override
  public boolean isConfigured() {
    return ALL_CACHE_MANAGERS.isEmpty()
        || ALL_CACHE_MANAGERS.values().stream()
            .allMatch(cacheManager -> Status.AVAILABLE.equals(cacheManager.getStatus()));
  }

  protected CacheManager getCacheManager(String tenantId) {
    return ALL_CACHE_MANAGERS.get(tenantId);
  }

  protected void saveCacheManager(String tenantId, CacheManager cacheManager) {
    ALL_CACHE_MANAGERS.put(tenantId, cacheManager);
  }

  protected abstract CacheManager cacheManagerProvider();

  protected abstract <T extends Serializable> Cache<String, T> cacheProvider(
      final String alias,
      final Long expireMillis,
      final Long size,
      final Class<T> resultType,
      CacheManager manager);

  private <T extends Serializable> Cache<String, T> getCacheInternal(
      final String tenantId,
      final String cacheName,
      final Long expireMillis,
      final Long size,
      final Class<T> resultType) {
    final String alias = buildCacheAlias(tenantId, cacheName);
    CacheManager manager = getCacheManager(tenantId);

    if (manager == null) {
      manager = cacheManagerProvider();
      saveCacheManager(tenantId, manager);
    }

    try {
      Cache<String, T> cache = manager.getCache(alias, String.class, resultType);

      if (cache == null) {
        cache = cacheProvider(alias, expireMillis, size, resultType, manager);
      }
      return cache;
    } catch (IllegalArgumentException iae) {
      throw new RuntimeException(iae);
    }
  }
}
