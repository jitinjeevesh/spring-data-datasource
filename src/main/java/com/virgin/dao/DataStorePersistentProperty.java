package com.virgin.dao;

import org.springframework.data.mapping.PersistentProperty;

public interface DataStorePersistentProperty extends PersistentProperty<DataStorePersistentProperty> {

    String getFieldName();

}
