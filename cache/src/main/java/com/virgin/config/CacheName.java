package com.virgin.config;

public enum CacheName {

    DEFAULT("defaultCache"),
    USER_CACHE("userCache");

    private final String value;

    public String getValue() {
        return value;
    }

    CacheName(String value) {
        this.value = value;
    }
}
