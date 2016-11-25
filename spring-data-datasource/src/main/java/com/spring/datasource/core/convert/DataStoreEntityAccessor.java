package com.spring.datasource.core.convert;

import com.google.cloud.datastore.DatastoreException;
import com.google.cloud.datastore.Entity;
import com.google.cloud.datastore.Value;
import com.spring.datasource.mapping.DataStorePersistentProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

public class DataStoreEntityAccessor {

    private static final Logger LOG = LoggerFactory.getLogger(DataStoreEntityAccessor.class);

    private final Entity entity;

    public DataStoreEntityAccessor(Entity entity) {
        Assert.notNull(entity, "DataStore Entity must not be null!");
        this.entity = entity;
    }

    public Object get(DataStorePersistentProperty property) {
        Value dataStoreValue = null;
        try {
            dataStoreValue = entity.getValue(property.getFieldName());
        } catch (DatastoreException exception) {
            LOG.error("No value found for" + property.getFieldName());
        }
        return dataStoreValue;
    }
}
