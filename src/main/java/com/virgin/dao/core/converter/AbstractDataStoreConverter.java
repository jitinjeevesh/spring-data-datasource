package com.virgin.dao.core.converter;

import org.springframework.beans.factory.InitializingBean;

public abstract class AbstractDataStoreConverter implements DataStoreConverter, InitializingBean {

    public String test() {
        return "This is the test AbstractDataStoreConverter";
    }

    public void afterPropertiesSet() {

    }
}
