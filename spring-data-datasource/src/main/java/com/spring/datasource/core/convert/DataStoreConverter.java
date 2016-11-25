package com.spring.datasource.core.convert;

import com.google.cloud.datastore.Entity;
import com.spring.datasource.mapping.DataStorePersistentEntity;
import com.spring.datasource.mapping.DataStorePersistentProperty;
import org.springframework.data.convert.EntityConverter;
import org.springframework.data.convert.EntityReader;

public interface DataStoreConverter extends EntityConverter<DataStorePersistentEntity<?>, DataStorePersistentProperty, Object, Entity>,
        DataStoreWriter<Object>, DataStoreReader {
}
