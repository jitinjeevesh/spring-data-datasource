package com.virgin.dao;

import com.google.cloud.datastore.Entity;
import org.springframework.data.convert.EntityConverter;
import org.springframework.data.convert.EntityReader;

public interface DataStoreConverter extends EntityConverter<DataStorePersistentEntity<?>, DataStorePersistentProperty, Object, Entity>,
        DataStoreWriter<Object>, EntityReader<Object, Entity> {
}
