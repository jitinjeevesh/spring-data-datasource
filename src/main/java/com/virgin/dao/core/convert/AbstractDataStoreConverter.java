package com.virgin.dao.core.convert;

import org.springframework.beans.factory.InitializingBean;

public abstract class AbstractDataStoreConverter implements DataStoreConverter, InitializingBean {

    public String test() {
        return "This is the test AbstractDataStoreConverter";
    }

    public void afterPropertiesSet() {

    }
}
