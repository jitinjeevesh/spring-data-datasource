package com.virgin.dao.core;

import com.google.cloud.datastore.Datastore;
import com.virgin.dao.core.converter.DataStoreConverter;

/**
 * This class is used to interact with the cloud datastore.
 */
public class DefaultDataStoreEntityManager implements DataStoreEntityManager {

    private final Datastore datastore;

    public DefaultDataStoreEntityManager() {
        this(SimpleDataStoredFactory.getInstance().getDefaultDataStore());
    }

    public DefaultDataStoreEntityManager(Datastore datastore) {
        this.datastore = datastore;
    }

    @Override
    public <E> E load(Class<E> entityClass, String id) {
        return null;
    }

    @Override
    public <E> E load(Class<E> entityClass, long id) {
        return null;
    }

    @Override
    public DataStoreConverter getConverter() {
        return null;
    }

    @Override
    public String test() {
        return "Hello this is the test method";
    }
}
