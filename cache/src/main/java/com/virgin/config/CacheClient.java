package com.virgin.config;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

public interface CacheClient {

    <K, V> V get(K key, CacheName cacheName);

    <K, V> void put(K key, V value, CacheName cacheName);

    <K, V> long count(CacheName cacheName);

    <K, V> Map<K, V> all(CacheName cacheName);

    <K, V> Collection<V> values(CacheName cacheName);

    <K, V> Set<K> keySet(CacheName cacheName);

    <K, V> boolean containsKey(K key, CacheName cacheName);

    <K, V> void delete(K key, CacheName cacheName);

    <K, V> void clear(CacheName cacheName);

    <K,V >void putAsync(K key, V value, CacheName cacheName);
}
