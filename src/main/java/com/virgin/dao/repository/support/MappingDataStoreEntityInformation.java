package com.virgin.dao.repository.support;

import com.virgin.dao.core.exception.IDPropertyNotFoundException;
import com.virgin.dao.mapping.DataStorePersistentEntity;

import java.io.Serializable;

//Ref : JpaMetamodelEntityInformation
public class MappingDataStoreEntityInformation<T, ID extends Serializable> extends DataStoreEntityInformationSupport<T, ID> {

    public MappingDataStoreEntityInformation(DataStorePersistentEntity<T> dataStorePersistentEntity) {
        super(dataStorePersistentEntity);
    }

    @Override
    public ID getId(T entity) {
        return super.getId(entity);
    }

    @Override
    public Class<ID> getIdType() {
        if (dataStorePersistentEntity.getIdProperty() == null) {
            throw new IDPropertyNotFoundException("No ID property found in " + getJavaType().getSimpleName());
        }
        return super.getIdType();
    }
}
