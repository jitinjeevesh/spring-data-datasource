package com.virgin.config;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

@Component
public class CacheImpl<K, V> implements Cache<K, V>, InitializingBean {

    @Autowired
    private CacheConfig cacheConfig;
    private CacheClient cacheClient;

    @Override
    public void afterPropertiesSet() throws Exception {
        this.cacheClient = CacheClientFactory.getClient(cacheConfig);
    }

    @Override
    public V get(K key) {
        return get(key, CacheName.DEFAULT);
    }

    @Override
    public V get(K key, CacheName cacheName) {
        return cacheClient.get(key, cacheName);
    }

    @Override
    public void put(K key, V value) {
        put(key, value, CacheName.DEFAULT);
    }

    @Override
    public void put(K key, V value, CacheName cacheName) {
        cacheClient.put(key, value, cacheName);
    }

    @Override
    public void putAsync(K key, V value, CacheName cacheName) {
        cacheClient.putAsync(key, value, cacheName);
    }

    @Override
    public long count(CacheName cacheName) {
        return cacheClient.count(cacheName);
    }

    @Override
    public Map<K, V> all(CacheName cacheName) {
        return cacheClient.all(cacheName);
    }

    @Override
    public Collection<V> values(CacheName cacheName) {
        return cacheClient.values(cacheName);
    }

    @Override
    public Set<K> keySet(CacheName cacheName) {
        return cacheClient.keySet(cacheName);
    }

    @Override
    public boolean containsKey(K key, CacheName cacheName) {
        return cacheClient.containsKey(key, cacheName);
    }

    @Override
    public void delete(K key, CacheName cacheName) {
        cacheClient.delete(key, cacheName);
    }

    @Override
    public void clear(CacheName cacheName) {
        cacheClient.clear(cacheName);
    }
}
