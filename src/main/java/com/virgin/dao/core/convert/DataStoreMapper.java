package com.virgin.dao.core.convert;

import com.google.cloud.datastore.Value;
import com.google.cloud.datastore.ValueBuilder;

public interface DataStoreMapper {

    Value<?> convert(Object input);

    Object convert(Value<?> input);
}
