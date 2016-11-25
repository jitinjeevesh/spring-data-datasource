package com.spring.datasource.core.convert;

import com.google.cloud.datastore.Entity;
import org.springframework.data.convert.EntityReader;

public interface DataStoreReader extends EntityReader<Object, Entity> {

    Object getIdValue(Class<?> type, Object obj);
}
