package com.virgin.dao.repository.support;

import com.virgin.dao.core.DataStoreOperation;
import com.virgin.dao.mapping.DataStorePersistentEntity;
import com.virgin.dao.mapping.DataStorePersistentProperty;
import com.virgin.dao.repository.query.DataStoreQueryMethod;
import com.virgin.dao.repository.query.PartTreeDataStoreQuery;
import com.virgin.dao.repository.query.StringBasedDataStoreQuery;
import org.springframework.data.mapping.context.MappingContext;
import org.springframework.data.mapping.model.MappingException;
import org.springframework.data.projection.ProjectionFactory;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.repository.core.NamedQueries;
import org.springframework.data.repository.core.RepositoryInformation;
import org.springframework.data.repository.core.RepositoryMetadata;
import org.springframework.data.repository.core.support.RepositoryFactorySupport;
import org.springframework.data.repository.query.EvaluationContextProvider;
import org.springframework.data.repository.query.QueryLookupStrategy;
import org.springframework.data.repository.query.RepositoryQuery;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.util.Assert;

import java.io.Serializable;
import java.lang.reflect.Method;

import static org.springframework.data.querydsl.QueryDslUtils.QUERY_DSL_PRESENT;

public class DataStoreRepositoryFactory extends RepositoryFactorySupport {

    private static final SpelExpressionParser EXPRESSION_PARSER = new SpelExpressionParser();
    private final MappingContext<? extends DataStorePersistentEntity<?>, DataStorePersistentProperty> mappingContext;
    private final DataStoreOperation dataStoreOperation;

    public DataStoreRepositoryFactory(DataStoreOperation dataStoreOperation) {
        Assert.notNull(dataStoreOperation, "DataStoreOperation can not be null inside DataStoreRepositoryFactory");
        this.dataStoreOperation = dataStoreOperation;
        this.mappingContext = dataStoreOperation.getConverter().getMappingContext();
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T, ID extends Serializable> DataStoreEntityInformation<T, ID> getEntityInformation(Class<T> domainClass) {
        DataStorePersistentEntity<?> dataStorePersistentEntity = mappingContext.getPersistentEntity(domainClass);
        if (dataStorePersistentEntity == null) {
            throw new MappingException(String.format("Could not lookup mapping metadata for domain class %s!", domainClass.getName()));
        }
        return (DataStoreEntityInformation<T, ID>) DataStoreEntityInformationSupport.getEntityInformation((DataStorePersistentEntity<T>) dataStorePersistentEntity);
    }

    @Override
    protected Object getTargetRepository(RepositoryInformation information) {
        DataStoreEntityInformation<?, Serializable> entityInformation = getEntityInformation(information.getDomainType());
        //Use setter here if required.
        return getTargetRepositoryViaReflection(information, entityInformation, dataStoreOperation);
    }

    //TODO: Check if Query dsl repository is used.
    @Override
    protected Class<?> getRepositoryBaseClass(RepositoryMetadata metadata) {
        boolean isQueryDslRepository = QUERY_DSL_PRESENT && QueryDslPredicateExecutor.class.isAssignableFrom(metadata.getRepositoryInterface());
//        return isQueryDslRepository ? SimpleDataStoreRepository.class : SimpleDataStoreRepository.class;
        return SimpleDataStoreRepository.class;
    }

    //TODO: Implement when query is used.
    @Override
    protected QueryLookupStrategy getQueryLookupStrategy(QueryLookupStrategy.Key key, EvaluationContextProvider evaluationContextProvider) {
        return new DataStoreQueryLookupStrategy(dataStoreOperation, evaluationContextProvider, mappingContext);
    }

    private static class DataStoreQueryLookupStrategy implements QueryLookupStrategy {

        private final DataStoreOperation operations;
        private final EvaluationContextProvider evaluationContextProvider;
        MappingContext<? extends DataStorePersistentEntity<?>, DataStorePersistentProperty> mappingContext;

        public DataStoreQueryLookupStrategy(DataStoreOperation operations, EvaluationContextProvider evaluationContextProvider,
                                            MappingContext<? extends DataStorePersistentEntity<?>, DataStorePersistentProperty> mappingContext) {

            this.operations = operations;
            this.evaluationContextProvider = evaluationContextProvider;
            this.mappingContext = mappingContext;
        }

        @Override
        public RepositoryQuery resolveQuery(Method method, RepositoryMetadata metadata, ProjectionFactory factory, NamedQueries namedQueries) {
            DataStoreQueryMethod queryMethod = new DataStoreQueryMethod(method, metadata, factory, mappingContext);
            String namedQueryName = queryMethod.getNamedQueryName();

            System.out.println("....................Inside resolve query......................................");
            System.out.printf(String.valueOf(namedQueries.hasQuery(namedQueryName)));
            System.out.println(metadata);
            System.out.println(method.getName());
            System.out.println(namedQueries);
            if (queryMethod.hasAnnotatedQuery()) {
                return new StringBasedDataStoreQuery(queryMethod, operations, EXPRESSION_PARSER, evaluationContextProvider);
            } else {
                return new PartTreeDataStoreQuery(queryMethod, operations);
            }
        }
    }
}
