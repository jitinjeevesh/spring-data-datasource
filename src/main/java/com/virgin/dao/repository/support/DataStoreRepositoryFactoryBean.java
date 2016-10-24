package com.virgin.dao.repository.support;

import com.virgin.dao.core.DataStoreOperation;
import org.springframework.data.mapping.context.MappingContext;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.core.support.RepositoryFactoryBeanSupport;
import org.springframework.data.repository.core.support.RepositoryFactorySupport;
import org.springframework.util.Assert;

import java.io.Serializable;

public class DataStoreRepositoryFactoryBean<T extends Repository<S, ID>, S, ID extends Serializable> extends
        RepositoryFactoryBeanSupport<T, S, ID> {

    private DataStoreOperation dataStoreOperation;
    private boolean mappingContextConfigured = false;

    @Override
    protected RepositoryFactorySupport createRepositoryFactory() {
        return getFactoryInstance(dataStoreOperation);
    }

    public DataStoreOperation getDataStoreOperation() {
        return dataStoreOperation;
    }

    public void setDataStoreOperation(DataStoreOperation dataStoreOperation) {
        this.dataStoreOperation = dataStoreOperation;
    }

    protected RepositoryFactorySupport getFactoryInstance(DataStoreOperation operations) {
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
        Assert.notNull(dataStoreOperation, "Datastore template must not be null!");

       /* if (!mappingContextConfigured) {
            setMappingContext(dataStoreOperation.getConverter().getMappingContext());
        }*/
    }
}
