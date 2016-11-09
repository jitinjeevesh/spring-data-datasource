package com.virgin.dao.core.convert.mappers;

import com.google.cloud.datastore.*;
import com.virgin.dao.core.convert.DataStoreMapper;

public class ByteArrayDataStoreMapper implements DataStoreMapper {

    @Override
    public ValueBuilder<?, ?, ?> toDatastore(Object input) {
        if (input == null) {
            return NullValue.newBuilder();
        }
        return BlobValue.newBuilder(Blob.copyFrom((byte[]) input));
    }

    @Override
    public Object toModel(Value<?> input) {
        if (input instanceof NullValue) {
            return null;
        }
        return ((BlobValue) input).get().toByteArray();
    }

}
