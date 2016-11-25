package com.spring.datasource.mapping;

import com.google.cloud.datastore.Value;
import com.spring.datasource.core.convert.DataStoreMapper;
import org.springframework.data.mapping.PersistentProperty;

public interface DataStorePersistentProperty extends PersistentProperty<DataStorePersistentProperty> {

    String getFieldName();

    boolean isExplicitIdProperty();

    DataStoreMapper getDataStoreMapper();

    Object getConvertibleValue(Value<?> input);

    Value<?> getConvertibleValue(Object input);

    Class<? extends Value<?>> getConvertibleType();
}
