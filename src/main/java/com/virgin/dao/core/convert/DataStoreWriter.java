package com.virgin.dao.core.convert;

import com.google.cloud.datastore.BaseEntity;
import com.google.cloud.datastore.Entity;
import com.google.cloud.datastore.Key;
import com.google.cloud.datastore.KeyFactory;
import com.virgin.dao.core.query.DynamicQuery;
import org.springframework.data.convert.EntityWriter;
import org.springframework.data.util.TypeInformation;

public interface DataStoreWriter<T> extends EntityWriter<T, Entity> {

    Object convertToDataStoreType(Object obj);

    Key getKey(Object idValue, KeyFactory keyFactory);

    Object convertToDataStoreType(Object obj, TypeInformation<?> typeInformation);

    BaseEntity<?> convertToDataStoreType(Object obj, Key key);

    BaseEntity<?> convertToDataStoreType(Object obj, KeyFactory keyFactory);

    BaseEntity<?> convertToDataStoreType(DynamicQuery bindings, Key key, Entity oldEntity);

//    DBRef toDBRef(Object object, MongoPersistentProperty referingProperty);
}
