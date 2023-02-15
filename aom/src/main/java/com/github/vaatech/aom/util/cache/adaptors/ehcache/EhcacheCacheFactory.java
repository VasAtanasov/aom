package com.github.vaatech.aom.util.cache.adaptors.ehcache;

import org.ehcache.Cache;
import org.ehcache.CacheManager;
import org.ehcache.config.CacheConfiguration;
import org.ehcache.config.ResourcePools;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.ExpiryPolicyBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.ehcache.config.units.MemoryUnit;

import java.io.Serializable;
import java.time.Duration;
import java.util.Objects;

class EhcacheCacheFactory {

  static <T extends Serializable> Cache<String, T> heap(
      final String alias,
      final long expireMills,
      final long size,
      final Class<T> resultType,
      CacheManager manager) {
    return create(manager, ResourceType.HEAP, alias, expireMills, size, resultType, false);
  }

  static <T extends Serializable> Cache<String, T> disk(
      final String alias,
      final long expireMills,
      final long size,
      final Class<T> resultType,
      CacheManager manager) {
    return create(manager, ResourceType.DISK, alias, expireMills, size, resultType, true);
  }

  private static <T extends Serializable> Cache<String, T> create(
      final CacheManager manager,
      final ResourceType type,
      final String alias,
      final long expireMills,
      final long size,
      final Class<T> resultType,
      final boolean persistable) {

    Objects.requireNonNull(type);

    ResourcePools pool =
        switch (type) {
          case HEAP -> ResourcePoolsBuilder.heap(size).build();
          case DISK -> ResourcePoolsBuilder.newResourcePoolsBuilder()
              .disk(size, MemoryUnit.MB, persistable)
              .build();
        };

    CacheConfiguration<String, T> cacheConfiguration =
        CacheConfigurationBuilder.newCacheConfigurationBuilder(String.class, resultType, pool)
            .withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ofMillis(expireMills)))
            .build();

    return manager.createCache(alias, cacheConfiguration);
  }
}
