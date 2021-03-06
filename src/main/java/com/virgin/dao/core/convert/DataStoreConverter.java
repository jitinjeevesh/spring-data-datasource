package com.virgin.dao.core.convert;

import com.google.cloud.datastore.Entity;
import com.virgin.dao.mapping.DataStorePersistentEntity;
import com.virgin.dao.mapping.DataStorePersistentProperty;
import org.springframework.data.convert.EntityConverter;
import org.springframework.data.convert.EntityReader;

public interface DataStoreConverter extends EntityConverter<DataStorePersistentEntity<?>, DataStorePersistentProperty, Object, Entity>,
        DataStoreWriter<Object>, EntityReader<Object, Entity> {
}
