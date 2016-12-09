package com.spring.datasource.repository.query;

import com.spring.datasource.core.query.DynamicQuery;
import com.spring.datasource.core.query.Criteria;
import com.spring.datasource.mapping.DataStorePersistentProperty;
import com.spring.datasource.repository.query.parser.AbstractDataStoreQueryCreator;
import com.spring.datasource.repository.query.parser.DataStorePartTree;
import org.springframework.data.domain.Sort;
import org.springframework.data.mapping.context.MappingContext;
import org.springframework.data.mapping.context.PersistentPropertyPath;
import org.springframework.data.repository.query.parser.AbstractQueryCreator;
import org.springframework.data.repository.query.parser.Part;
import org.springframework.data.repository.query.parser.PartTree;
import org.springframework.util.Assert;

import java.util.Collection;
import java.util.Iterator;

public class DataStoreQueryCreator extends AbstractDataStoreQueryCreator<DynamicQuery, Criteria> {

    private final MappingContext<?, DataStorePersistentProperty> context;
    private final DataStoreParameterAccessor accessor;

    public DataStoreQueryCreator(DataStorePartTree tree, DataStoreConvertingParameterAccessor parameterAccessor, MappingContext<?, DataStorePersistentProperty> context) {
        super(tree, parameterAccessor);
        Assert.notNull(context, "MappingContext must not be null");
        this.context = context;
        this.accessor = parameterAccessor;
    }

    @Override
    protected Criteria create(Part part, Iterator<Object> iterator) {
        PersistentPropertyPath<DataStorePersistentProperty> path = context.getPersistentPropertyPath(part.getProperty());
        DataStorePersistentProperty property = path.getLeafProperty();
        Criteria criteria = from(part, property, Criteria.where(path.toDotPath()), iterator);
        return criteria;
    }

    @Override
    protected Criteria and(Part part, Criteria base, Iterator<Object> iterator) {
        if (base == null) {
            return create(part, iterator);
        }
        PersistentPropertyPath<DataStorePersistentProperty> path = context.getPersistentPropertyPath(part.getProperty());
        DataStorePersistentProperty property = path.getLeafProperty();
        return from(part, property, base.and(path.toDotPath()), (DataStoreConvertingParameterAccessor.PotentiallyConvertingIterator) iterator);
    }

    @Override
    protected Criteria or(Criteria base, Criteria criteria) {
//        Criteria result = new Criteria();
//        return result.orOperator(base, criteria);
        return null;
    }

    @Override
    protected DynamicQuery complete(Criteria criteria, Sort sort) {
        DynamicQuery dynamicQuery = (criteria == null ? new DynamicQuery() : new DynamicQuery(criteria)).with(sort);
        return dynamicQuery;
    }

    private Criteria from(Part part, DataStorePersistentProperty property, Criteria criteria, Iterator<Object> parameters) {
        Part.Type type = part.getType();
        switch (type) {
            case SIMPLE_PROPERTY:
                Object o = parameters.next();
                return criteria.is(o);
            case IN:
                return criteria.in(nextAsArray(parameters));
            default:
                throw new IllegalArgumentException("Unsupported query type");
        }
    }

    private Object[] nextAsArray(Iterator<Object> iterator) {

        Object next = iterator.next();

        if (next instanceof Collection) {
            return ((Collection<?>) next).toArray();
        } else if (next != null && next.getClass().isArray()) {
            return (Object[]) next;
        }

        return new Object[]{next};
    }

    private boolean isSimpleComparisionPossible(Part part) {

        switch (part.shouldIgnoreCase()) {
            case NEVER:
                return true;
            case WHEN_POSSIBLE:
                return part.getProperty().getType() != String.class;
            case ALWAYS:
                return false;
            default:
                return true;
        }
    }

}
