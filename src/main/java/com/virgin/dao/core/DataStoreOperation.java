package com.virgin.dao.core;

import com.virgin.dao.core.convert.DataStoreConverter;

public interface DataStoreOperation {

    <E> E load(Class<E> entityClass, String id);

    <E> E load(Class<E> entityClass, long id);

    DataStoreConverter getConverter();
}
