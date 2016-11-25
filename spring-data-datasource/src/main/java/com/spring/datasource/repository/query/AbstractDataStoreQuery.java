package com.spring.datasource.repository.query;

import com.spring.datasource.core.DataStoreOperation;
import com.spring.datasource.core.query.DataStoreQuery;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.EntityInstantiators;
import org.springframework.data.repository.query.QueryMethod;
import org.springframework.data.repository.query.RepositoryQuery;
import org.springframework.util.Assert;

public abstract class AbstractDataStoreQuery implements RepositoryQuery {

    private final DataStoreQueryMethod method;
    private final DataStoreOperation operations;
    private final EntityInstantiators instantiators;

    public AbstractDataStoreQuery(DataStoreQueryMethod dataStoreQueryMethod, DataStoreOperation dataStoreOperation) {
        Assert.notNull(dataStoreOperation, "DataStoreOperation must not be null!");
        Assert.notNull(dataStoreQueryMethod, "DataStoreQueryMethod must not be null!");
        this.method = dataStoreQueryMethod;
        this.operations = dataStoreOperation;
        this.instantiators = new EntityInstantiators();
    }

    @Override
    public Object execute(Object[] parameters) {
        DataStoreParameterAccessor dataStoreParameterAccessor = new DataStoreParametersParameterAccessor(method, parameters);
        DataStoreQuery dataStoreQuery = createQuery(new DataStoreConvertingParameterAccessor(operations.getConverter(), dataStoreParameterAccessor));
        String kindName = method.getEntityInformation().getKindName();
        DataStoreQueryExecution.ResultProcessingConverter resultProcessingConverter = new DataStoreQueryExecution.ResultProcessingConverter(operations, instantiators);
        DataStoreQueryExecution dataStoreQueryExecution = getExecution(dataStoreQuery, dataStoreParameterAccessor, resultProcessingConverter);
        return dataStoreQueryExecution.execute(dataStoreQuery, method.getEntityInformation().getJavaType(), kindName);
    }

    private DataStoreQueryExecution getExecution(DataStoreQuery dataStoreQuery, DataStoreParameterAccessor accessor, Converter<Object, Object> resultProcessing) {
        return new DataStoreQueryExecution.ResultProcessingExecution(getExecutionToWrap(dataStoreQuery, accessor), resultProcessing);
    }

    //TODO:Implement all queries.
    private DataStoreQueryExecution getExecutionToWrap(DataStoreQuery dynamicQuery, DataStoreParameterAccessor accessor) {
        System.out.println("..........................Check for collection query................................................");
        System.out.println(method.isCollectionQuery());
        if (method.isCollectionQuery()) {
            return new DataStoreQueryExecution.CollectionExecution(operations, accessor.getPageable());
        } else if (isUpdateQuery()) {
            return new DataStoreQueryExecution.UpdateEntityExecution(operations);
        } else if (isDeleteQuery()) {
            return null;
        } else {
            return new DataStoreQueryExecution.SingleEntityExecution(operations, isCountQuery());
        }
    }

    @Override
    public QueryMethod getQueryMethod() {
        return method;
    }

    protected abstract DataStoreQuery createQuery(DataStoreConvertingParameterAccessor accessor);

    protected abstract boolean isCountQuery();

    protected abstract boolean isDeleteQuery();

    protected abstract boolean isUpdateQuery();
}
