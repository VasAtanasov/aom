package com.github.vaatech.aom.util.cache;

import com.github.vaatech.aom.util.cache.adaptors.ehcache.EhcacheDiskCacheAdapter;
import com.github.vaatech.aom.util.cache.adaptors.ehcache.EhcacheHeapCacheAdapter;
import com.github.vaatech.aom.util.cache.adaptors.CacheAdapter;

import java.util.Arrays;

public class Test {
  public static void main(String[] args) {

    CacheAdapter ehcacheHeapCacheAdapter = new EhcacheHeapCacheAdapter();
    CacheAdapter ehcacheDiskCacheAdapter = new EhcacheDiskCacheAdapter(null);

    CacheProviderServiceImpl service =
        new CacheProviderServiceImpl(
            Arrays.asList(ehcacheDiskCacheAdapter, ehcacheHeapCacheAdapter));

    CacheAdapter manager = service.getCacheAdapter(CacheProviderType.EHCACHE_HEAP);

    String value =
        manager.computeIfAbsent(
            "Tenant1", "some_name", 60_000L, 100L, "aaa", String.class, () -> "xxx");

    String newValue =
        manager.computeIfAbsent(
            "Tenant1", "some_name", 60_000L, 100L, "aaa", String.class, () -> "bbb");
    String newValue2 =
        manager.computeIfAbsent(
            "Tenant1", "some_name2", 60_000L, 100L, "aaa", String.class, () -> "bbb");

    int a = 5;
  }
}
