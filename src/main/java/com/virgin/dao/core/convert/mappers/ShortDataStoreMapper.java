package com.virgin.dao.core.convert.mappers;

import com.google.cloud.datastore.LongValue;
import com.google.cloud.datastore.NullValue;
import com.google.cloud.datastore.Value;
import com.google.cloud.datastore.ValueBuilder;
import com.virgin.dao.core.convert.DataStoreMapper;
import com.virgin.dao.core.exception.MappingException;

public class ShortDataStoreMapper implements DataStoreMapper {

    @Override
    public ValueBuilder<?, ?, ?> toDatastore(Object input) {
        if (input == null) {
            return NullValue.newBuilder();
        }
        return LongValue.newBuilder((short) input);
    }

    @Override
    public Object toModel(Value<?> input) {
        if (input instanceof NullValue) {
            return null;
        }
        Long l = ((LongValue) input).get();
        if (l < Short.MIN_VALUE || l > Short.MAX_VALUE) {
            throw new MappingException(String.format("Value %d is out of range for short type", l));
        }
        return l.shortValue();
    }

}
