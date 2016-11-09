package com.virgin.dao.core.convert.mappers;

import com.google.cloud.datastore.NullValue;
import com.google.cloud.datastore.StringValue;
import com.google.cloud.datastore.Value;
import com.google.cloud.datastore.ValueBuilder;
import com.virgin.dao.core.convert.DataStoreMapper;
import com.virgin.dao.core.exception.MappingException;

public class CharDataStoreMapper implements DataStoreMapper {

    @Override
    public ValueBuilder<?, ?, ?> toDatastore(Object input) {
        if (input == null) {
            return NullValue.newBuilder();
        }
        return StringValue.newBuilder(String.valueOf((char) input));
    }

    @Override
    public Object toModel(Value<?> input) {
        if (input instanceof NullValue) {
            return null;
        }
        String str = ((StringValue) input).get();
        if (str.length() != 1) {
            throw new MappingException(String.format("Unable to convert %s to char", str));
        }
        return str.charAt(0);
    }

}
