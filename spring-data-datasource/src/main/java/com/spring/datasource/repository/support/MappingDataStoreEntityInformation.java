package com.spring.datasource.repository.support;

import com.spring.datasource.core.exception.IDPropertyNotFoundException;
import com.spring.datasource.mapping.DataStorePersistentEntity;

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
