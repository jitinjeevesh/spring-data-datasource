package com.virgin.dao;

import org.springframework.data.mapping.context.MappingContext;
import org.springframework.data.mapping.model.MappingException;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.repository.core.RepositoryInformation;
import org.springframework.data.repository.core.RepositoryMetadata;
import org.springframework.data.repository.core.support.RepositoryFactorySupport;
import org.springframework.data.repository.query.EvaluationContextProvider;
import org.springframework.data.repository.query.QueryLookupStrategy;

import java.io.Serializable;

import static org.springframework.data.querydsl.QueryDslUtils.QUERY_DSL_PRESENT;

public class DataStoreRepositoryFactory extends RepositoryFactorySupport {

    private final MappingContext<? extends DataStorePersistentEntity<?>, DataStorePersistentProperty> mappingContext;
    private final DataStoreEntityManager dataStoreEntityManager;

    public DataStoreRepositoryFactory(DataStoreEntityManager dataStoreEntityManager) {
        this.dataStoreEntityManager = dataStoreEntityManager;
        this.mappingContext = dataStoreEntityManager.getConverter().getMappingContext();
    }

    @Override
    public <T, ID extends Serializable> DataStoreEntityInformation<T, ID> getEntityInformation(Class<T> domainClass) {
        return getEntityInformation(domainClass, null);
    }

    private <T, ID extends Serializable> DataStoreEntityInformation<T, ID> getEntityInformation(Class<T> domainClass, RepositoryInformation information) {
        DataStorePersistentEntity<?> entity = mappingContext.getPersistentEntity(domainClass);
        if (entity == null) {
            throw new MappingException(String.format("Could not lookup mapping metadata for domain class %s!", domainClass.getName()));
        }
        return new MappingDataStoreEntityInformation<>((DataStorePersistentEntity<T>) entity, information != null ? (Class<ID>) information.getIdType() : null);
    }


    @Override
    protected Object getTargetRepository(RepositoryInformation information) {
        DataStoreEntityInformation<?, Serializable> entityInformation = getEntityInformation(information.getDomainType(), information);
        return getTargetRepositoryViaReflection(information, entityInformation, dataStoreEntityManager);
    }

    //TODO: Check is Query dsl repository is used.
    @Override
    protected Class<?> getRepositoryBaseClass(RepositoryMetadata metadata) {
        boolean isQueryDslRepository = QUERY_DSL_PRESENT && QueryDslPredicateExecutor.class.isAssignableFrom(metadata.getRepositoryInterface());
        return isQueryDslRepository ? SimpleDataStoreRepository.class : SimpleDataStoreRepository.class;
    }


    //TODO: Implement when query is used.
    @Override
    protected QueryLookupStrategy getQueryLookupStrategy(QueryLookupStrategy.Key key, EvaluationContextProvider evaluationContextProvider) {
        return null;
    }
}
