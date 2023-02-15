package com.github.vaatech.aom.util.cache;

import com.github.vaatech.aom.util.cache.adaptors.CacheAdapter;

public interface CacheProviderService {
  CacheAdapter getCacheAdapter(CacheProviderType type);
}
