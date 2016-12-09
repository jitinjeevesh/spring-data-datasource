package com.virgin.config;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IList;
import com.hazelcast.core.IMap;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

public class HazelcastCacheClient implements CacheClient {

    private final CacheConfig cacheConfig;
    private final HazelcastInstance hazelcastInstance;

    public HazelcastCacheClient(CacheConfig cacheConfig) {
        this.cacheConfig = cacheConfig;
        this.hazelcastInstance = HazelcastCacheInstance.getConfig(cacheConfig.getServerUrl());
    }

    @Override
    public <K, V> V get(K key, CacheName cacheName) {
        IMap<K, V> map = hazelcastInstance.getMap(cacheName.getValue());
        IList list = hazelcastInstance.getList(cacheName.getValue());
        return map.get(key);
    }

    @Override
    public <K, V> void put(K key, V value, CacheName cacheName) {
        IMap<K, V> map = hazelcastInstance.getMap(cacheName.getValue());
        map.put(key, value);
    }

    @Override
    public <K, V> void putAsync(K key, V value, CacheName cacheName) {
        IMap<K, V> map = hazelcastInstance.getMap(cacheName.getValue());
        map.putAsync(key, value);
    }

    @Override
    public <K, V> long count(CacheName cacheName) {
        IMap<K, V> map = hazelcastInstance.getMap(cacheName.getValue());
        return map.size();
    }

    @Override
    public <K, V> Map<K, V> all(CacheName cacheName) {
        return hazelcastInstance.getMap(cacheName.getValue());
    }

    @SuppressWarnings("unchecked")
    @Override
    public <K, V> Collection<V> values(CacheName cacheName) {
        return (Collection<V>) hazelcastInstance.getMap(cacheName.getValue()).values();
    }

    @SuppressWarnings("unchecked")
    @Override
    public <K, V> Set<K> keySet(CacheName cacheName) {
        return (Set<K>) hazelcastInstance.getMap(cacheName.getValue()).keySet();
    }

    @Override
    public <K, V> boolean containsKey(K key, CacheName cacheName) {
        return hazelcastInstance.getMap(cacheName.getValue()).containsKey(key);
    }

    @Override
    public <K, V> void delete(K key, CacheName cacheName) {
        hazelcastInstance.getMap(cacheName.getValue()).delete(key);
    }

    @Override
    public <K, V> void clear(CacheName cacheName) {
        hazelcastInstance.getMap(cacheName.getValue()).clear();
    }
}
