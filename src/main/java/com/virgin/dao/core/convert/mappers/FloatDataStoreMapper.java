package com.virgin.dao.core.convert.mappers;

import com.google.cloud.datastore.DoubleValue;
import com.google.cloud.datastore.NullValue;
import com.google.cloud.datastore.Value;
import com.google.cloud.datastore.ValueBuilder;
import com.virgin.dao.core.convert.DataStoreMapper;
import com.virgin.dao.core.exception.MappingException;

public class FloatDataStoreMapper implements DataStoreMapper {

    @Override
    public ValueBuilder<?, ?, ?> toDatastore(Object input) {
        if (input == null) {
            return NullValue.newBuilder();
        }
        return DoubleValue.newBuilder((float) input);
    }

    @Override
    public Object toModel(Value<?> input) {
        if (input instanceof NullValue) {
            return null;
        }
        DoubleValue value = (DoubleValue) input;
        Double d = value.get();
        if (d < Float.MIN_VALUE || d > Float.MAX_VALUE) {
            throw new MappingException(String.format("Value %f is out of range for float type", d));
        }
        return d.floatValue();
    }

}
