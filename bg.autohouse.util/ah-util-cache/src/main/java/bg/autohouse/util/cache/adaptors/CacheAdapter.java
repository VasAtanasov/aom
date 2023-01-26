package bg.autohouse.util.cache.adaptors;

import bg.autohouse.util.cache.CacheProviderType;

import java.io.Serializable;
import java.util.function.Supplier;

public interface CacheAdapter {

  <T extends Serializable> T computeIfAbsent(
      final String tenantId,
      final String cacheName,
      final Long expireMillis,
      final Long size,
      final String key,
      final Class<T> resultType,
      final Supplier<T> valueSupplier);

  CacheProviderType getType();

  boolean isConfigured();

  default String buildCacheAlias(final String prefix, final String cacheName) {
    return (prefix + ":" + cacheName).toLowerCase();
  }
}
