package com.virgin.dao.core.convert.mappers;

import com.google.cloud.datastore.NullValue;
import com.google.cloud.datastore.StringValue;
import com.google.cloud.datastore.Value;
import com.google.cloud.datastore.ValueBuilder;
import com.virgin.dao.core.convert.DataStoreMapper;

public class EnumDataStoreMapper implements DataStoreMapper {

    @SuppressWarnings("rawtypes")
    private Class enumClass;

    @SuppressWarnings("rawtypes")
    public EnumDataStoreMapper(Class enumClass) {
        this.enumClass = enumClass;
    }

    @Override
    public ValueBuilder<?, ?, ?> toDatastore(Object input) {
        if (input == null) {
            return NullValue.newBuilder();
        }
        return StringValue.newBuilder(input.toString());
    }

    @SuppressWarnings("unchecked")
    @Override
    public Object toModel(Value<?> input) {
        if (input instanceof NullValue) {
            return null;
        }
        String value = ((StringValue) input).get();
        return Enum.valueOf(enumClass, value);
    }

}
