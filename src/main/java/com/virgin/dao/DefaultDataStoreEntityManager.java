package com.virgin.dao;

import com.google.cloud.datastore.*;
import com.jmethods.catatumbo.impl.EntityIntrospector;
import com.jmethods.catatumbo.impl.EntityMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DefaultDataStoreEntityManager implements DataStoreEntityManager {

    private static final Logger LOG = LoggerFactory.getLogger(DefaultDataStoreEntityManager.class);
    private final DataStoreConverter dataStoreConverter;
    private Datastore datastore;

    private DatastoreReader nativeReader = null;

    public DefaultDataStoreEntityManager(Datastore datastore, DataStoreConverter dataStoreConverter) {
        LOG.info("Constructor initialized for : DefaultDataStoreEntityManager");
        this.datastore = datastore;
        this.nativeReader = datastore;
        this.dataStoreConverter = dataStoreConverter;
    }

    @Override
    public <E> E load(Class<E> entityClass, String id) {
        try {
            EntityMetadata entityMetadata = EntityIntrospector.introspect(entityClass);
            Key key = datastore.newKeyFactory().kind(entityMetadata.getKind()).newKey(id);
            Entity nativeEntity = nativeReader.get(key);
            System.out.println("......................................................");
            System.out.println(nativeEntity);
        } catch (DatastoreException exp) {
            exp.printStackTrace();
        }
        return null;
    }

    @Override
    public <E> E load(Class<E> entityClass, long id) {
        try {
            EntityMetadata entityMetadata = EntityIntrospector.introspect(entityClass);
            Key key = datastore.newKeyFactory().kind(entityMetadata.getKind()).newKey(id);
            Entity nativeEntity = nativeReader.get(key);
            System.out.println("......................................................");
            System.out.println(nativeEntity);
        } catch (DatastoreException exp) {
            exp.printStackTrace();
        }
        return null;
    }

    @Override
    public DataStoreConverter getConverter() {
        return this.dataStoreConverter;
    }
}
