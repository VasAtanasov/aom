package bg.autohouse.backend.util.cache;

import bg.autohouse.backend.util.cache.adaptors.CacheAdapter;

public interface CacheProviderService {
  CacheAdapter getCacheAdapter(CacheProviderType type);
}
