package com.virgin.dao.repository.support;

import com.virgin.dao.mapping.DataStorePersistentEntity;
import com.virgin.dao.repository.query.DataStoreEntityMetaData;
import com.virgin.dao.repository.query.DefaultDataStoreEntityMetaData;
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
        this.metadata = new DefaultDataStoreEntityMetaData<T>(dataStorePersistentEntity.getType());
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
