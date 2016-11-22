package com.virgin.dao.repository.query;

import com.virgin.dao.core.DataStoreOperation;
import com.virgin.dao.core.query.DynamicQuery;
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
    protected DynamicQuery createQuery(DataStoreConvertingParameterAccessor accessor) {
        DataStoreQueryCreator dataStoreQueryCreator = new DataStoreQueryCreator(tree, accessor, context);
        DynamicQuery dynamicQuery = dataStoreQueryCreator.createQuery();
        System.out.println("........................Inside create query....................................");
        System.out.println(dynamicQuery);
        System.out.println(tree.isLimiting());
        System.out.println(tree.getMaxResults());
        if (tree.isLimiting()) {
            dynamicQuery.limit(tree.getMaxResults());
        }
        ReturnedType returnedType = processor.withDynamicProjection(accessor).getReturnedType();
        System.out.println(returnedType.isProjecting());
        System.out.println(returnedType.getReturnedType());
        return dynamicQuery;
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
