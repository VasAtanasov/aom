package com.github.vaatech.aom.cache.adaptors;

import com.github.vaatech.aom.cache.CacheProviderType;

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
