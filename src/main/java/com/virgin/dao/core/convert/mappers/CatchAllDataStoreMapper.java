package com.virgin.dao.core.convert.mappers;

import com.google.cloud.datastore.*;
import com.virgin.dao.core.convert.DataStoreMapper;
import com.virgin.dao.core.exception.MappingException;

public class CatchAllDataStoreMapper implements DataStoreMapper {

    private static final CatchAllDataStoreMapper INSTANCE = new CatchAllDataStoreMapper();

    private CatchAllDataStoreMapper() {
        // Do nothing
    }

    public static DataStoreMapper getInstance() {
        return INSTANCE;
    }

    @Override
    public ValueBuilder<?, ?, ?> toDatastore(Object input) {
        ValueBuilder<?, ?, ?> builder;
        if (input == null) {
            builder = NullValue.newBuilder();
        } else if (input instanceof Long) {
            builder = LongValue.newBuilder((long) input);
        } else if (input instanceof Double) {
            builder = DoubleValue.newBuilder((double) input);
        } else if (input instanceof Boolean) {
            builder = BooleanValue.newBuilder((boolean) input);
        } else if (input instanceof String) {
            builder = StringValue.newBuilder((String) input);
        } else if (input instanceof Key) {
            builder = KeyValue.newBuilder(((Key) input));
        } else {
            throw new MappingException(String.format("Unsupported type: %s", input.getClass().getName()));
        }
        return builder;
    }

    @Override
    public Object toModel(Value<?> input) {
        Object javaValue;
        if (input instanceof NullValue) {
            javaValue = null;
        } else if (input instanceof StringValue) {
            javaValue = input.get();
        } else if (input instanceof LongValue) {
            javaValue = input.get();
        } else if (input instanceof DoubleValue) {
            javaValue = input.get();
        } else if (input instanceof BooleanValue) {
            javaValue = input.get();
        } else if (input instanceof KeyValue) {
            javaValue = ((KeyValue) input).get();
        } else {
            throw new MappingException(String.format("Unsupported type %s", input.getClass().getName()));
        }
        return javaValue;
    }

}
