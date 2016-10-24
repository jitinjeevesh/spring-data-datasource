package com.virgin.dao.core.converter;

import com.google.cloud.datastore.Entity;
import org.springframework.data.convert.EntityWriter;
import org.springframework.data.util.TypeInformation;

public interface DataStoreWriter<T> extends EntityWriter<T, Entity> {

    Object convertToDataStoreType(Object obj);

    Object convertToDataStoreType(Object obj, TypeInformation<?> typeInformation);

//    DBRef toDBRef(Object object, MongoPersistentProperty referingProperty);
}
