package com.virgin.dao.repository.support;

import com.virgin.dao.DataStoreEntityManager;
import org.springframework.data.mapping.context.MappingContext;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.core.support.RepositoryFactoryBeanSupport;
import org.springframework.data.repository.core.support.RepositoryFactorySupport;
import org.springframework.util.Assert;

import java.io.Serializable;

public class DataStoreRepositoryFactoryBean<T extends Repository<S, ID>, S, ID extends Serializable> extends
        RepositoryFactoryBeanSupport<T, S, ID> {

    private DataStoreEntityManager dataStoreEntityManager;
    private boolean mappingContextConfigured = false;

    @Override
    protected RepositoryFactorySupport createRepositoryFactory() {
        return getFactoryInstance(dataStoreEntityManager);
    }

    public DataStoreEntityManager getDataStoreEntityManager() {
        return dataStoreEntityManager;
    }

    protected RepositoryFactorySupport getFactoryInstance(DataStoreEntityManager operations) {
        return new DataStoreRepositoryFactory(operations);
    }

    @Override
    protected void setMappingContext(MappingContext<?, ?> mappingContext) {
        super.setMappingContext(mappingContext);
        this.mappingContextConfigured = true;
    }

    @Override
    public void afterPropertiesSet() {

        super.afterPropertiesSet();
        Assert.notNull(dataStoreEntityManager, "Datastore template must not be null!");

        if (!mappingContextConfigured) {
            setMappingContext(dataStoreEntityManager.getConverter().getMappingContext());
        }
    }
}
