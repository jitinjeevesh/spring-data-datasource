package com.virgin.dao.core;

import com.google.cloud.datastore.*;
import com.jmethods.catatumbo.impl.EntityIntrospector;
import com.jmethods.catatumbo.impl.EntityMetadata;
import com.virgin.dao.core.converter.DataStoreConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DataStoreTemplate implements DataStoreOperation {

    private static final Logger LOG = LoggerFactory.getLogger(DataStoreTemplate.class);
    private final DataStoreConverter dataStoreConverter;
    private DataStoreEntityManager dataStoreEntityManager;
    //TODO:Remove this field. This is just for testing purpose.
    private String name;

//    private DatastoreReader nativeReader = null;

    public DataStoreTemplate() {
        this(null, null);
    }

    //TODO:Temp. use only
    public DataStoreTemplate(DataStoreEntityManager dataStoreEntityManager) {
        this(dataStoreEntityManager, null);
        System.out.println("Inside DataStoreTemplate constructor");
        System.out.println(dataStoreEntityManager.test());
    }

    public DataStoreTemplate(DataStoreEntityManager dataStoreEntityManager, DataStoreConverter dataStoreConverter) {
        LOG.info("Constructor initialized for : DataStoreTemplate");
        this.dataStoreEntityManager = dataStoreEntityManager;
        this.dataStoreConverter = dataStoreConverter;
        System.out.println("Inside DataStoreTemplate constructor");
        System.out.println(dataStoreEntityManager.test());
        System.out.println(".............................................................");
        System.out.println(dataStoreConverter.test());
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
           /* EntityMetadata entityMetadata = EntityIntrospector.introspect(entityClass);
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
    public DataStoreConverter getConverter() {
        return this.dataStoreConverter;
    }
}
