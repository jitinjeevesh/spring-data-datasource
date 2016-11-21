package com.virgin.dao.repository.query;

import org.springframework.data.repository.core.EntityMetadata;

public interface DataStoreEntityMetaData<T> extends EntityMetadata<T> {

    String getKindName();

}
