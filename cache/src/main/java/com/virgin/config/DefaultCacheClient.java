package com.virgin.config;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

public class DefaultCacheClient implements CacheClient {

    private final DefaultCache defaultCache;

    public DefaultCacheClient(CacheConfig cacheConfig) {
        this.defaultCache = new DefaultCache();
    }

    @Override
    public <K, V> V get(K key, CacheName cacheName) {
        return null;
    }

    @Override
    public <K, V> void put(K key, V value, CacheName cacheName) {
    }

    @Override
    public <K, V> long count(CacheName cacheName) {
        return 0;
    }

    @Override
    public <K, V> Map<K, V> all(CacheName cacheName) {
        return null;
    }

    @Override
    public <K, V> Collection<V> values(CacheName cacheName) {
        return null;
    }

    @Override
    public <K, V> Set<K> keySet(CacheName cacheName) {
        return null;
    }

    @Override
    public <K, V> boolean containsKey(K key, CacheName cacheName) {
        return false;
    }

    @Override
    public <K, V> void delete(K key, CacheName cacheName) {

    }

    @Override
    public <K, V> void clear(CacheName cacheName) {

    }

    @Override
    public <K, V> void putAsync(K key, V value, CacheName cacheName) {

    }
}
