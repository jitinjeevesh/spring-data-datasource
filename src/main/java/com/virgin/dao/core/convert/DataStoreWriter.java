package com.virgin.dao.core.convert;

import com.google.cloud.datastore.BaseEntity;
import com.google.cloud.datastore.Entity;
import com.google.cloud.datastore.Key;
import org.springframework.data.convert.EntityWriter;
import org.springframework.data.util.TypeInformation;

public interface DataStoreWriter<T> extends EntityWriter<T, Entity> {

    Object convertToDataStoreType(Object obj);

    Object convertToDataStoreType(Object obj, TypeInformation<?> typeInformation);

    BaseEntity<?> convertToDataStoreType(Object obj, Key key);

//    DBRef toDBRef(Object object, MongoPersistentProperty referingProperty);
}
