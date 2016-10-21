package com.virgin.dao;

import org.springframework.data.repository.core.support.PersistentEntityInformation;

import java.io.Serializable;

public class MappingDataStoreEntityInformation<T, ID extends Serializable> extends PersistentEntityInformation<T, ID> implements DataStoreEntityInformation<T, ID> {

    private final DataStorePersistentEntity<T> entityMetadata;
    private final Class<ID> fallbackIdType;

    public MappingDataStoreEntityInformation(DataStorePersistentEntity<T> entity) {
        this(entity, null);
    }

    public MappingDataStoreEntityInformation(DataStorePersistentEntity<T> entity, Class<ID> fallbackIdType) {
        super(entity);
        this.entityMetadata = entity;
        this.fallbackIdType = fallbackIdType;
    }

    @Override
    public String getKindName() {
        return entityMetadata.getKind();
    }
}
