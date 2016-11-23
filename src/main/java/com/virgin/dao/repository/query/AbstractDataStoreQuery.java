package com.virgin.dao.repository.query;

import com.virgin.dao.core.DataStoreOperation;
import com.virgin.dao.core.query.DataStoreQuery;
import com.virgin.dao.repository.query.DataStoreQueryExecution.SingleEntityExecution;
import com.virgin.dao.repository.query.DataStoreQueryExecution.UpdateEntityExecution;
import com.virgin.dao.repository.query.DataStoreQueryExecution.ResultProcessingConverter;
import com.virgin.dao.repository.query.DataStoreQueryExecution.ResultProcessingExecution;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.EntityInstantiators;
import org.springframework.data.repository.query.QueryMethod;
import org.springframework.data.repository.query.RepositoryQuery;
import org.springframework.data.repository.query.ResultProcessor;
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
        ResultProcessor processor = method.getResultProcessor().withDynamicProjection(dataStoreParameterAccessor);
        String kindName = method.getEntityInformation().getKindName();
        ResultProcessingConverter resultProcessingConverter = new ResultProcessingConverter(processor, operations, instantiators);
        DataStoreQueryExecution dataStoreQueryExecution = getExecution(dataStoreQuery, dataStoreParameterAccessor, resultProcessingConverter);
        return dataStoreQueryExecution.execute(dataStoreQuery, processor.getReturnedType().getDomainType(), kindName);
    }

    private DataStoreQueryExecution getExecution(DataStoreQuery dataStoreQuery, DataStoreParameterAccessor accessor, Converter<Object, Object> resultProcessing) {
        return new ResultProcessingExecution(getExecutionToWrap(dataStoreQuery, accessor), resultProcessing);
    }

    //TODO:Implement all queries.
    private DataStoreQueryExecution getExecutionToWrap(DataStoreQuery dynamicQuery, DataStoreParameterAccessor accessor) {
        System.out.println("..........................Check for collection query................................................");
        System.out.println(method.isCollectionQuery());
        if (method.isCollectionQuery()) {
            return null;
        } else if (isUpdateQuery()) {
            System.out.println("This is a update query............................................................");
            return new UpdateEntityExecution(operations);
        } else {
            return new SingleEntityExecution(operations, isCountQuery());
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
