package com.virgin.dao.core.convert.mappers;

import com.google.cloud.datastore.*;
import com.virgin.dao.core.convert.DataStoreMapper;

import java.util.Date;

public class DateDataStoreMapper implements DataStoreMapper {

    @Override
    public ValueBuilder<?, ?, ?> toDatastore(Object input) {
        if (input == null) {
            return NullValue.newBuilder();
        }
        return DateTimeValue.newBuilder(DateTime.copyFrom((Date) input));
    }

    @Override
    public Object toModel(Value<?> input) {
        if (input instanceof NullValue) {
            return null;
        }
        return ((DateTimeValue) input).get().toDate();
    }

}
