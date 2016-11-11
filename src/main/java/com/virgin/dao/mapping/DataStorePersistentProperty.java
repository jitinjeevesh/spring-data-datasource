package com.virgin.dao.mapping;

import com.google.cloud.datastore.Value;
import com.virgin.dao.core.convert.DataStoreMapper;
import org.springframework.data.mapping.PersistentProperty;

public interface DataStorePersistentProperty extends PersistentProperty<DataStorePersistentProperty> {

    String getFieldName();

    boolean isExplicitIdProperty();

    DataStoreMapper getDataStoreMapper();

    Object getConvertibleValue(Value<?> input);

    Class<? extends Value<?>> getConvertibleType();
}
