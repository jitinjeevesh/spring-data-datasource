package com.virgin.config;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

public interface Cache<K, V> {

    V get(K key);

    V get(K key, CacheName cacheName);

    void put(K key, V value);

    void put(K key, V value, CacheName cacheName);

    long count(CacheName cacheName);

    Map<K, V> all(CacheName cacheName);

    Collection<V> values(CacheName cacheName);

    Set<K> keySet(CacheName cacheName);

    boolean containsKey(K key, CacheName cacheName);

    void delete(K key, CacheName cacheName);

    void clear(CacheName cacheName);
}
