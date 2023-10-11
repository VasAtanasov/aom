package com.github.vaatech.aom.cache;

import com.github.vaatech.aom.cache.adaptors.CacheAdapter;

public interface CacheProviderService {
    CacheAdapter getCacheAdapter(CacheProviderType type);
}
