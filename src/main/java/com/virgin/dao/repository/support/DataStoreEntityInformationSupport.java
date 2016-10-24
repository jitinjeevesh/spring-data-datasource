package com.virgin.dao.repository.support;

import com.virgin.dao.repository.query.DataStoreEntityMetaData;
import com.virgin.dao.repository.query.DefaultDataStoreEntityMetaData;
import org.springframework.data.repository.core.support.AbstractEntityInformation;
import org.springframework.util.Assert;

import java.io.Serializable;

public abstract class DataStoreEntityInformationSupport<T, ID extends Serializable> extends AbstractEntityInformation<T, ID>
        implements DataStoreEntityInformation<T, ID> {

    private DataStoreEntityMetaData<T> metadata;

    public DataStoreEntityInformationSupport(Class<T> domainClass) {
        super(domainClass);
        this.metadata = new DefaultDataStoreEntityMetaData<T>(domainClass);
    }

    public static <T> DataStoreEntityInformation<T, ?> getEntityInformation(Class<T> domainClass) {
        Assert.notNull(domainClass);
//        if (Persistable.class.isAssignableFrom(domainClass)) {
//            return new JpaPersistableEntityInformation(domainClass, metamodel);
//        } else {
        return new MappingDataStoreEntityInformation(domainClass);
//        }
    }

    @Override
    public String getKindName() {
        return metadata.getKindName();
    }
}
