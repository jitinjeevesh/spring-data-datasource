package com.virgin.config;

public enum CacheType {
    HAZELCAST("Hazelcast");

    private final String value;

    public String getValue() {
        return this.value;
    }

    CacheType(String value) {
        this.value = value;
    }
}
