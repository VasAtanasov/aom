package com.github.vaatech.aom.cache;

import com.github.vaatech.aom.cache.adaptors.CacheAdapter;

import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

public class CacheProviderServiceImpl implements CacheProviderService {

    private final List<CacheAdapter> adapters;

    public CacheProviderServiceImpl(List<CacheAdapter> adapters) {
        this.adapters = adapters;
    }

    private static Supplier<IllegalStateException> notConfigured(
            final CacheProviderType cacheProviderType) {
        return () -> new IllegalStateException("Storage is not configured: " + cacheProviderType);
    }

    private Optional<CacheAdapter> findStorage(final CacheProviderType cacheProviderType) {
        return adapters.stream()
                .filter(ca -> ca.getType() == cacheProviderType && ca.isConfigured())
                .findAny();
    }

    @Override
    public CacheAdapter getCacheAdapter(final CacheProviderType type) {
        return findStorage(type).orElseThrow(notConfigured(type));
    }
}
