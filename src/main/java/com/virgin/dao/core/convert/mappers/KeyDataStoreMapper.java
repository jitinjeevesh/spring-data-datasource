package com.virgin.dao.core.convert.mappers;

import com.google.cloud.datastore.KeyValue;
import com.google.cloud.datastore.NullValue;
import com.google.cloud.datastore.Value;
import com.google.cloud.datastore.ValueBuilder;
import com.jmethods.catatumbo.DatastoreKey;
import com.jmethods.catatumbo.DefaultDatastoreKey;
import com.virgin.dao.core.convert.DataStoreMapper;

public class KeyDataStoreMapper implements DataStoreMapper {

    @Override
    public ValueBuilder<?, ?, ?> toDatastore(Object input) {
        if (input == null) {
            return NullValue.newBuilder();
        }
        DatastoreKey datastoreKey = (DatastoreKey) input;
        return KeyValue.newBuilder(datastoreKey.nativeKey());
    }

    @Override
    public Object toModel(Value<?> input) {
        if (input instanceof NullValue) {
            return null;
        }
        KeyValue keyValue = (KeyValue) input;
        return new DefaultDatastoreKey(keyValue.get());
    }

}
