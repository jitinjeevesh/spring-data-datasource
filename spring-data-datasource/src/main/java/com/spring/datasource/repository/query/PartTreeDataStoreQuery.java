package com.spring.datasource.repository.query;

import com.spring.datasource.core.DataStoreOperation;
import com.spring.datasource.core.query.DynamicQuery;
import com.spring.datasource.mapping.DataStorePersistentProperty;
import com.spring.datasource.repository.query.parser.DataStorePartTree;
import org.springframework.data.mapping.context.MappingContext;

public class PartTreeDataStoreQuery extends AbstractDataStoreQuery {

    private final DataStorePartTree tree;
    private final MappingContext<?, DataStorePersistentProperty> context;

    public PartTreeDataStoreQuery(DataStoreQueryMethod dataStoreQueryMethod, DataStoreOperation dataStoreOperation) {
        super(dataStoreQueryMethod, dataStoreOperation);
        this.context = dataStoreOperation.getConverter().getMappingContext();
        this.tree = new DataStorePartTree(dataStoreQueryMethod.getName(), getQueryMethod().getEntityInformation().getJavaType());
    }

    @Override
    protected DynamicQuery createQuery(DataStoreConvertingParameterAccessor accessor) {
        DataStoreQueryCreator dataStoreQueryCreator = new DataStoreQueryCreator(tree, accessor, context);
        DynamicQuery dynamicQuery = dataStoreQueryCreator.createQuery();
        if (tree.isLimiting()) {
            dynamicQuery.limit(tree.getMaxResults());
        }
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

    @Override
    protected boolean isUpdateQuery() {
        return tree.isUpdate();
    }
}
