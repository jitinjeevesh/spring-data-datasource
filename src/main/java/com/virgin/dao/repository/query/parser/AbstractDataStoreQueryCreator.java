package com.virgin.dao.repository.query.parser;

import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.ParameterAccessor;
import org.springframework.data.repository.query.parser.Part;
import org.springframework.util.Assert;

import java.util.Iterator;

public abstract class AbstractDataStoreQueryCreator<T, S> {

    private final ParameterAccessor parameters;
    private final DataStorePartTree tree;

    public AbstractDataStoreQueryCreator(DataStorePartTree tree, ParameterAccessor parameters) {
        Assert.notNull(tree, "DataStorePartTree must not be null");

        this.tree = tree;
        this.parameters = parameters;
    }

    public AbstractDataStoreQueryCreator(DataStorePartTree tree) {

        this(tree, null);
    }

    public T createQuery() {
        Sort dynamicSort = parameters != null ? parameters.getSort() : null;
        return createQuery(dynamicSort);
    }

    public T createQuery(Sort dynamicSort) {

        Sort staticSort = tree.getSort();
        Sort sort = staticSort != null ? staticSort.and(dynamicSort) : dynamicSort;

        return complete(createCriteria(tree), sort);
    }

    private S createCriteria(DataStorePartTree tree) {

        S base = null;
        Iterator<Object> iterator = parameters == null ? null : parameters.iterator();

        for (DataStorePartTree.OrPart node : tree) {

            S criteria = null;

            for (Part part : node) {

                criteria = criteria == null ? create(part, iterator) : and(part, criteria, iterator);
            }

            base = base == null ? criteria : or(base, criteria);
        }

        return base;
    }

    protected abstract S create(Part part, Iterator<Object> iterator);

    protected abstract S and(Part part, S base, Iterator<Object> iterator);

    protected abstract S or(S base, S criteria);

    protected abstract T complete(S criteria, Sort sort);
}
