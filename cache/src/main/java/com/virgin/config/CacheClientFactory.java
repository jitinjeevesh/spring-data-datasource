package com.virgin.config;

public class CacheClientFactory {

    public static CacheClient _cacheClient = null;
    private static Object lock = new Object();

    public static CacheClient getClient(CacheConfig cacheConfig) {
        if (_cacheClient == null) {
            synchronized (lock) {
                if (cacheConfig.getType().equalsIgnoreCase(CacheType.HAZELCAST.getValue())) {
                    _cacheClient = new HazelcastCacheClient(cacheConfig);
                } else {
                    _cacheClient = new DefaultCacheClient(cacheConfig);
                }
            }
        } else {
            return _cacheClient;
        }
        return _cacheClient;
    }

}
