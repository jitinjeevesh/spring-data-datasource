package com.virgin.dao.core.convert;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class DataStoreCache<K, V> {

    private static final float LOAD_FACTOR = 0.75f;
    private static final int DEFAULT_CAPACITY = 16;
    private Map<K, V> map;

    public DataStoreCache() {
        this(DEFAULT_CAPACITY, LOAD_FACTOR);
    }

    public DataStoreCache(int initialSize) {
        this(initialSize, LOAD_FACTOR);
    }

    public DataStoreCache(int initialSize, float loadFactor) {
        map = new ConcurrentHashMap<>(initialSize, loadFactor);
    }

    public V get(K key) {
        return map.get(key);
    }

    public V put(K key, V value) {
        return map.put(key, value);
    }

    public boolean containsKey(K key) {
        return map.containsKey(key);
    }

    public int size() {
        return map.size();
    }
}
