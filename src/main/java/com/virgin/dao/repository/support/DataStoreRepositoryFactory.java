package com.virgin.dao.repository.support;

import com.virgin.dao.DataStoreEntityManager;
import com.virgin.dao.SimpleDataStoreRepository;
import org.springframework.data.repository.core.RepositoryInformation;
import org.springframework.data.repository.core.RepositoryMetadata;
import org.springframework.data.repository.core.support.RepositoryFactorySupport;
import org.springframework.data.repository.query.EvaluationContextProvider;
import org.springframework.data.repository.query.QueryLookupStrategy;
import org.springframework.util.Assert;

import java.io.Serializable;

public class DataStoreRepositoryFactory extends RepositoryFactorySupport {

    //    private final MappingContext<? extends DataStoreEntityMetaData<?>, DataStorePersistentProperty> mappingContext;
    private final DataStoreEntityManager dataStoreEntityManager;

    public DataStoreRepositoryFactory(DataStoreEntityManager dataStoreEntityManager) {
        Assert.notNull(dataStoreEntityManager, "DataStoreEntityManager can not be null inside DataStoreRepositoryFactory");
        this.dataStoreEntityManager = dataStoreEntityManager;
//        this.mappingContext = dataStoreEntityManager.getConverter().getMappingContext();
    }

    @Override
    public <T, ID extends Serializable> DataStoreEntityInformation<T, ID> getEntityInformation(Class<T> domainClass) {
        return (DataStoreEntityInformation<T, ID>) DataStoreEntityInformationSupport.getEntityInformation(domainClass);
//        return getEntityInformation(domainClass, null);
    }

/*    private <T, ID extends Serializable> DataStoreEntityInformation<T, ID> getEntityInformation(Class<T> domainClass, RepositoryInformation information) {
        DataStoreEntityMetaData<?> entity = mappingContext.getPersistentEntity(domainClass);
        if (entity == null) {
            throw new MappingException(String.format("Could not lookup mapping metadata for domain class %s!", domainClass.getName()));
        }
//        return (DataStoreEntityInformation<T, ID>) DataStoreEntityInformationSupport.getEntityInformation(domainClass);
        return new MappingDataStoreEntityInformation<T, ID>(domainClass);
    }*/


    @Override
    protected Object getTargetRepository(RepositoryInformation information) {
        DataStoreEntityInformation<?, Serializable> entityInformation = getEntityInformation(information.getDomainType());
        //Use setter here if required.
        return getTargetRepositoryViaReflection(information, entityInformation, dataStoreEntityManager);
    }

    //TODO: Check is Query dsl repository is used.
    @Override
    protected Class<?> getRepositoryBaseClass(RepositoryMetadata metadata) {
//        boolean isQueryDslRepository = QUERY_DSL_PRESENT && QueryDslPredicateExecutor.class.isAssignableFrom(metadata.getRepositoryInterface());
//        return isQueryDslRepository ? SimpleDataStoreRepository.class : SimpleDataStoreRepository.class;
        return SimpleDataStoreRepository.class;
    }

    //TODO: Implement when query is used.
    @Override
    protected QueryLookupStrategy getQueryLookupStrategy(QueryLookupStrategy.Key key, EvaluationContextProvider evaluationContextProvider) {
        return null;
    }
}
