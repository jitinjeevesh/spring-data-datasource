package com.virgin.dao;

import org.springframework.data.mapping.context.MappingContext;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.core.support.RepositoryFactoryBeanSupport;
import org.springframework.data.repository.core.support.RepositoryFactorySupport;
import org.springframework.util.Assert;

import java.io.Serializable;

public class DataStoreRepositoryFactoryBean<T extends Repository<S, ID>, S, ID extends Serializable> extends
        RepositoryFactoryBeanSupport<T, S, ID> {

    private DataStoreEntityManager dataStoreEntityManager;
    private boolean createIndexesForQueryMethods = false;
    private boolean mappingContextConfigured = false;

    @Override
    protected RepositoryFactorySupport createRepositoryFactory() {
        RepositoryFactorySupport factory = getFactoryInstance(dataStoreEntityManager);

        //TODO:Implement if query method is indexed
       /* if (createIndexesForQueryMethods) {
            factory.addQueryCreationListener(new IndexEnsuringQueryCreationListener(operations));
        }*/
        return factory;
    }

    public DataStoreEntityManager getDataStoreEntityManager() {
        return dataStoreEntityManager;
    }

    public void setCreateIndexesForQueryMethods(boolean createIndexesForQueryMethods) {
        this.createIndexesForQueryMethods = createIndexesForQueryMethods;
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
        Assert.notNull(dataStoreEntityManager, "MongoTemplate must not be null!");

        if (!mappingContextConfigured) {
            setMappingContext(dataStoreEntityManager.getConverter().getMappingContext());
        }
    }
}
