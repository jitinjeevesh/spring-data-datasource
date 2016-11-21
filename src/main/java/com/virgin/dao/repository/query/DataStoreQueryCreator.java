package com.virgin.dao.repository.query;

import com.virgin.dao.repository.query.DataStoreConvertingParameterAccessor.PotentiallyConvertingIterator;
import com.virgin.dao.core.query.Criteria;
import com.virgin.dao.core.query.DataStoreQuery;
import com.virgin.dao.mapping.DataStorePersistentProperty;
import org.springframework.data.domain.Sort;
import org.springframework.data.mapping.context.MappingContext;
import org.springframework.data.mapping.context.PersistentPropertyPath;
import org.springframework.data.repository.query.parser.AbstractQueryCreator;
import org.springframework.data.repository.query.parser.Part;
import org.springframework.data.repository.query.parser.PartTree;
import org.springframework.util.Assert;

import java.util.Iterator;

public class DataStoreQueryCreator extends AbstractQueryCreator<DataStoreQuery, Criteria> {

    private final MappingContext<?, DataStorePersistentProperty> context;
    private final DataStoreParameterAccessor accessor;

    public DataStoreQueryCreator(PartTree tree, DataStoreConvertingParameterAccessor parameterAccessor, MappingContext<?, DataStorePersistentProperty> context) {
        super(tree, parameterAccessor);
        Assert.notNull(context, "MappingContext must not be null");
        this.context = context;
        this.accessor = parameterAccessor;
    }

    @Override
    protected Criteria create(Part part, Iterator<Object> iterator) {
        System.out.println("Inside create block.......................");
        PersistentPropertyPath<DataStorePersistentProperty> path = context.getPersistentPropertyPath(part.getProperty());
        DataStorePersistentProperty property = path.getLeafProperty();
        Criteria criteria = from(part, property, Criteria.where(path.toDotPath()), iterator);
        System.out.println(property);
        System.out.println(part);
        return criteria;
    }

    @Override
    protected Criteria and(Part part, Criteria base, Iterator<Object> iterator) {
        System.out.println(".............>>Inside and block................");
        if (base == null) {
            return create(part, iterator);
        }
        PersistentPropertyPath<DataStorePersistentProperty> path = context.getPersistentPropertyPath(part.getProperty());
        DataStorePersistentProperty property = path.getLeafProperty();

        return from(part, property, base.and(path.toDotPath()), (PotentiallyConvertingIterator) iterator);
    }


    @Override
    protected Criteria or(Criteria base, Criteria criteria) {
        System.out.println(".............>>Inside or block................");
//        Criteria result = new Criteria();
//        return result.orOperator(base, criteria);
        return null;
    }

    @Override
    protected DataStoreQuery complete(Criteria criteria, Sort sort) {
        System.out.println("Inside complete block.........");
        System.out.println(criteria);
        System.out.println(sort);
        DataStoreQuery dataStoreQuery = (criteria == null ? new DataStoreQuery() : new DataStoreQuery(criteria)).with(sort);
        return dataStoreQuery;
    }

    private Criteria from(Part part, DataStorePersistentProperty property, Criteria criteria, Iterator<Object> parameters) {
        Part.Type type = part.getType();
        switch (type) {
            case SIMPLE_PROPERTY:
                System.out.println("Is simple comparision possible");
                System.out.println(isSimpleComparisionPossible(part));
                Object o = parameters.next();
                System.out.println(o);
                return criteria.is(o);
            default:
                throw new IllegalArgumentException("Unsupported query type");
        }
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
