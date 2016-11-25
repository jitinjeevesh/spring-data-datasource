package com.spring.datasource.repository.query;

import org.springframework.data.repository.core.EntityMetadata;

public interface DataStoreEntityMetaData<T> extends EntityMetadata<T> {

    String getKindName();

}
