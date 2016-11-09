package com.virgin.dao.core.convert.mappers;

import com.google.cloud.datastore.DoubleValue;
import com.google.cloud.datastore.NullValue;
import com.google.cloud.datastore.Value;
import com.google.cloud.datastore.ValueBuilder;
import com.virgin.dao.core.convert.DataStoreMapper;

public class DoubleDataStoreMapper implements DataStoreMapper {

    @Override
    public ValueBuilder<?, ?, ?> toDatastore(Object input) {
        if (input == null) {
            return NullValue.newBuilder();
        }
        return DoubleValue.newBuilder((double) input);
    }

    @Override
    public Object toModel(Value<?> input) {
        if (input instanceof NullValue) {
            return null;
        }
        return ((DoubleValue) input).get();
    }

}
