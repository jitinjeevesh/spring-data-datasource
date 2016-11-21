package com.virgin.dao.repository.query;

import com.virgin.dao.core.DataStoreOperation;
import com.virgin.dao.core.query.DataStoreQuery;
import com.virgin.dao.mapping.DataStorePersistentProperty;
import org.springframework.data.mapping.context.MappingContext;
import org.springframework.data.repository.query.ResultProcessor;
import org.springframework.data.repository.query.ReturnedType;
import org.springframework.data.repository.query.parser.PartTree;

public class PartTreeDataStoreQuery extends AbstractDataStoreQuery {

    private final PartTree tree;
    private final MappingContext<?, DataStorePersistentProperty> context;
    private final ResultProcessor processor;

    public PartTreeDataStoreQuery(DataStoreQueryMethod dataStoreQueryMethod, DataStoreOperation dataStoreOperation) {
        super(dataStoreQueryMethod, dataStoreOperation);
        this.processor = dataStoreQueryMethod.getResultProcessor();
        this.tree = new PartTree(dataStoreQueryMethod.getName(), processor.getReturnedType().getDomainType());
        this.context = dataStoreOperation.getConverter().getMappingContext();
    }

    @Override
    protected DataStoreQuery createQuery(DataStoreConvertingParameterAccessor accessor) {
        DataStoreQueryCreator dataStoreQueryCreator = new DataStoreQueryCreator(tree, accessor, context);
        DataStoreQuery dataStoreQuery = dataStoreQueryCreator.createQuery();
        System.out.println("........................Inside create query....................................");
        System.out.println(dataStoreQuery);
        System.out.println(tree.isLimiting());
        System.out.println(tree.getMaxResults());
        if (tree.isLimiting()) {
            dataStoreQuery.limit(tree.getMaxResults());
        }
        ReturnedType returnedType = processor.withDynamicProjection(accessor).getReturnedType();
        System.out.println(returnedType.isProjecting());
        System.out.println(returnedType.getReturnedType());
        return dataStoreQuery;
    }

    @Override
    protected boolean isCountQuery() {
        return tree.isCountProjection();
    }

    @Override
    protected boolean isDeleteQuery() {
        return tree.isDelete();
    }
}
