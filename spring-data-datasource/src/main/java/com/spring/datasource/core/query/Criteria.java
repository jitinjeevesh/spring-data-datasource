package com.spring.datasource.core.query;

import com.google.cloud.datastore.StructuredQuery.CompositeFilter;
import com.google.cloud.datastore.StructuredQuery.Filter;
import com.google.cloud.datastore.StructuredQuery.PropertyFilter;
import com.spring.datasource.core.convert.DataStoreMapperFactory;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class Criteria implements CriteriaDefinition {

    private static final Object NOT_SET = new Object();

    private String key;
    private List<Criteria> criteriaChain;
    private LinkedHashMap<String, Object> criteria = new LinkedHashMap<String, Object>();
    private Object isValue = NOT_SET;
    protected DataStoreMapperFactory dataStoreMapperFactory;

    {
        if (this.dataStoreMapperFactory == null) {
            dataStoreMapperFactory = DataStoreMapperFactory.getInstance();
        }
    }

    public Criteria() {
        this.criteriaChain = new ArrayList<Criteria>();
    }

    public Criteria(String key) {
        this.criteriaChain = new ArrayList<Criteria>();
        this.criteriaChain.add(this);
        this.key = key;
    }

    protected Criteria(List<Criteria> criteriaChain, String key) {
        this.criteriaChain = criteriaChain;
        this.criteriaChain.add(this);
        this.key = key;
    }

    public static Criteria where(String key) {
        return new Criteria(key);
    }

    public Criteria and(String key) {
        return new Criteria(this.criteriaChain, key);
    }

    public String getKey() {
        return this.key;
    }

    public Object getValue() {
        return this.isValue;
    }

    public Criteria is(Object o) {
        System.out.println("Criteria sets with object" + o);
        if (!isValue.equals(NOT_SET)) {
            throw new InvalidDataAccessApiUsageException("Multiple 'is' values declared. You need to use 'and' with multiple criteria");
        }
        /*if (lastOperatorWasNot()) {
            throw new InvalidDataAccessApiUsageException("Invalid query: 'not' can't be used with 'is' - use 'ne' instead.");
        }*/
        System.out.println("...............Inside is criteria.............................");
        this.isValue = o;
        return this;
    }

    /*private boolean lastOperatorWasNot() {
        return !this.criteria.isEmpty() && "not".equals(this.criteria.keySet().toArray()[this.criteria.size() - 1]);
    }
*/

    public Filter getCriteriaFilter() {
        if (this.criteriaChain.size() == 1) {
            return criteriaChain.get(0).getSingleCriteriaFilter();
        } else if (CollectionUtils.isEmpty(this.criteriaChain) && !CollectionUtils.isEmpty(this.criteria)) {
            return getSingleCriteriaFilter();
        } else {
            List<PropertyFilter> propertyFilters = new ArrayList<PropertyFilter>();
            for (Criteria criteria : criteriaChain) {
                propertyFilters.add(criteria.getSingleCriteriaFilter());
            }
            PropertyFilter firstPropertyFilter = propertyFilters.get(0);
            propertyFilters.remove(firstPropertyFilter);
            PropertyFilter[] restFilters = propertyFilters.toArray(new PropertyFilter[propertyFilters.size() - 1]);
            return CompositeFilter.and(firstPropertyFilter, restFilters);
        }
    }

    protected PropertyFilter getSingleCriteriaFilter() {
        PropertyFilter propertyFilter = PropertyFilter.eq(this.key, dataStoreMapperFactory.getMapperValue(this.isValue));
        return propertyFilter;
    }

    public List<ParameterBinding> getParameterBindings() {
        List<ParameterBinding> propertyFilters = new ArrayList<ParameterBinding>();
        for (Criteria criteria : criteriaChain) {
            if (criteria.getKey().equalsIgnoreCase("id"))
                continue;
            propertyFilters.add(criteria.getSingleParameterBinding());
        }
        return propertyFilters;
    }

    @Override
    public ParameterBinding getIdParameterBindings() {
        for (Criteria criteria : criteriaChain) {
            if (criteria.getKey().equalsIgnoreCase("id"))
                return criteria.getSingleParameterBinding();
        }
        return null;
    }

    protected ParameterBinding getSingleParameterBinding() {
        return new ParameterBinding(0, this.key, this.isValue);
    }

    public static class ParameterBinding {

        private final int parameterIndex;
        private final String name;
        private final Object value;

        public ParameterBinding(int parameterIndex, String name, Object value) {
            this.parameterIndex = parameterIndex;
            this.name = name;
            this.value = value;
        }

        public int getParameterIndex() {
            return parameterIndex;
        }

        public String getName() {
            return name;
        }

        public Object getValue() {
            return value;
        }
    }
}
