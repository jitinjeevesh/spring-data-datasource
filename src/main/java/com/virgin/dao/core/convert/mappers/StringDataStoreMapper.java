package com.virgin.dao.core.convert.mappers;

import com.google.cloud.datastore.NullValue;
import com.google.cloud.datastore.StringValue;
import com.google.cloud.datastore.Value;
import com.google.cloud.datastore.ValueBuilder;
import com.virgin.dao.core.convert.DataStoreMapper;

public class StringDataStoreMapper implements DataStoreMapper {

    @Override
    public ValueBuilder<?, ?, ?> toDatastore(Object input) {
        if (input == null) {
            return NullValue.newBuilder();
        }
        return StringValue.newBuilder((String) input);
    }

    @Override
    public Object toModel(Value<?> input) {
        if (input instanceof NullValue) {
            return null;
        }
        return ((StringValue) input).get();
    }

}
