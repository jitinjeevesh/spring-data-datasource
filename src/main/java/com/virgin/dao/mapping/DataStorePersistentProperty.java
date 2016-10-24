package com.virgin.dao.mapping;

import org.springframework.data.mapping.PersistentProperty;

public interface DataStorePersistentProperty extends PersistentProperty<DataStorePersistentProperty> {

    String getFieldName();

    boolean isExplicitIdProperty();

}
