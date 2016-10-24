package com.virgin.dao.repository.support;

import java.io.Serializable;

//Ref : JpaMetamodelEntityInformation
public class MappingDataStoreEntityInformation<T, ID extends Serializable> extends DataStoreEntityInformationSupport<T, ID> {

    public MappingDataStoreEntityInformation(Class<T> domainClass) {
        super(domainClass);
    }

    @Override
    public ID getId(T entity) {
        return null;
    }

    @Override
    public Class<ID> getIdType() {
        return null;
    }
}
