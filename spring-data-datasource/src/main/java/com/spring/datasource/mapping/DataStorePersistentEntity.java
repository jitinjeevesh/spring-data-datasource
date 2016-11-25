package com.spring.datasource.mapping;

import org.springframework.data.mapping.PersistentEntity;

public interface DataStorePersistentEntity<T> extends PersistentEntity<T, DataStorePersistentProperty> {

    String getKind();

}
