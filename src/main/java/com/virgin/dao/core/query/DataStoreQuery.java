package com.virgin.dao.core.query;

import com.google.cloud.datastore.StructuredQuery.Filter;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.data.domain.Sort;

import java.util.LinkedHashMap;
import java.util.Map;

public class DataStoreQuery {

    private final Map<String, CriteriaDefinition> criteria = new LinkedHashMap<String, CriteriaDefinition>();
    private int limit;
    private Sort sort;

    public static DataStoreQuery query(CriteriaDefinition criteriaDefinition) {
        return new DataStoreQuery(criteriaDefinition);
    }

    public DataStoreQuery() {
    }

    public DataStoreQuery(CriteriaDefinition criteriaDefinition) {
        addCriteria(criteriaDefinition);
    }

    public DataStoreQuery addCriteria(CriteriaDefinition criteriaDefinition) {
        CriteriaDefinition existing = this.criteria.get(criteriaDefinition.getKey());
        String key = criteriaDefinition.getKey();
        if (existing == null) {
            this.criteria.put(key, criteriaDefinition);
        } else {
            throw new InvalidDataAccessApiUsageException(
                    "Due to limitations of the data store you can not add ");
        }

        return this;
    }

    public DataStoreQuery limit(int limit) {
        this.limit = limit;
        return this;
    }

    public DataStoreQuery with(Sort sort) {
        if (sort == null) {
            return this;
        }
        /*for (Sort.Order order : sort) {
            if (order.isIgnoreCase()) {
                throw new IllegalArgumentException(String.format("Given sort contained an Order for %s with ignore case! "
                        + "DataStore does not support sorting ignoreing case currently!", order.getProperty()));
            }
        }*/

        if (this.sort == null) {
            this.sort = sort;
        } else {
            this.sort = this.sort.and(sort);
        }
        return this;
    }

    public Filter getPropertyFilter() {
        Filter filter = null;
        for (CriteriaDefinition definition : criteria.values()) {
            filter = definition.getCriteriaFilter();
        }
        return filter;
    }
}
