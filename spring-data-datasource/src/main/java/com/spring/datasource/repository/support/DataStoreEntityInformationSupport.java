package com.spring.datasource.repository.support;

import com.spring.datasource.mapping.DataStorePersistentEntity;
import com.spring.datasource.repository.query.DataStoreEntityMetaData;
import com.spring.datasource.repository.query.DefaultDataStoreEntityMetaData;
import org.springframework.data.repository.core.support.AbstractEntityInformation;
import org.springframework.data.repository.core.support.PersistentEntityInformation;
import org.springframework.util.Assert;

import java.io.Serializable;

public abstract class DataStoreEntityInformationSupport<T, ID extends Serializable> extends PersistentEntityInformation<T, ID>
        implements DataStoreEntityInformation<T, ID> {

    protected DataStoreEntityMetaData<T> metadata;
    protected final DataStorePersistentEntity<T> dataStorePersistentEntity;

    public DataStoreEntityInformationSupport(DataStorePersistentEntity<T> dataStorePersistentEntity) {
        super(dataStorePersistentEntity);
        this.dataStorePersistentEntity = dataStorePersistentEntity;
        this.metadata = new DefaultDataStoreEntityMetaData<T>(dataStorePersistentEntity.getType(),dataStorePersistentEntity);
    }

    public static <T> DataStoreEntityInformation<T, ?> getEntityInformation(DataStorePersistentEntity<T> dataStorePersistentEntity) {
        Assert.notNull(dataStorePersistentEntity);
        return new MappingDataStoreEntityInformation<>(dataStorePersistentEntity);
    }

    @Override
    public String getKindName() {
        return metadata.getKindName();
    }
}
