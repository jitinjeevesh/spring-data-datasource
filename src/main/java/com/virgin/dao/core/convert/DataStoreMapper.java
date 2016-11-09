package com.virgin.dao.core.convert;

import com.google.cloud.datastore.Value;
import com.google.cloud.datastore.ValueBuilder;

public interface DataStoreMapper {

    ValueBuilder<?, ?, ?> toDatastore(Object input);

    Object toModel(Value<?> input);
}
