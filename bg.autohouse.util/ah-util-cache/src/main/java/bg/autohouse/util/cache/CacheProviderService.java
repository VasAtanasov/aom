package bg.autohouse.util.cache;

import bg.autohouse.util.cache.adaptors.CacheAdapter;

public interface CacheProviderService {
  CacheAdapter getCacheAdapter(CacheProviderType type);
}
