package com.virgin.dao.core;

import com.google.cloud.datastore.*;
import com.virgin.dao.core.convert.DataStoreConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

public class DataStoreTemplate implements DataStoreOperation {

    private static final Logger LOG = LoggerFactory.getLogger(DataStoreTemplate.class);

    private static final String DATASTORE_MUST_NOT_BE_NULL = "Data store object must not be null";

    private final DataStoreConverter dataStoreConverter;
    private Datastore datastore;

    public DataStoreTemplate(Datastore datastore, DataStoreConverter dataStoreConverter) {
        Assert.notNull(datastore, DATASTORE_MUST_NOT_BE_NULL);
        this.datastore = datastore;
        this.dataStoreConverter = dataStoreConverter;
    }

    @Override
    public <E> E load(Class<E> entityClass, String id) {
        try {
            /*EntityMetadata entityMetadata = EntityIntrospector.introspect(entityClass);
            Key key = datastore.newKeyFactory().kind(entityMetadata.getKind()).newKey(id);
            Entity nativeEntity = nativeReader.get(key);*/
            System.out.println("......................................................");
//            System.out.println(nativeEntity);
        } catch (DatastoreException exp) {
            exp.printStackTrace();
        }
        return null;
    }

    @Override
    public <E> E load(Class<E> entityClass, long id) {
        try {
            System.out.println(entityClass.getSimpleName());
            KeyFactory keyFactory = datastore.newKeyFactory().kind(entityClass.getSimpleName());
            Key key = keyFactory.newKey(id);
            Entity entity = datastore.get(key);
            System.out.println("......................................................");
            System.out.println(entity);
            System.out.println("......................................................");
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
