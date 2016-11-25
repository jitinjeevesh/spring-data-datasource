package com.spring.datasource.core.query;

import com.google.cloud.datastore.StructuredQuery.Filter;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.*;

public class DynamicQuery implements DataStoreQuery {

    private final Map<String, CriteriaDefinition> criteria = new LinkedHashMap<String, CriteriaDefinition>();
    private int limit;
    private Sort sort;
    private int offset;

    public static DynamicQuery query(CriteriaDefinition criteriaDefinition) {
        return new DynamicQuery(criteriaDefinition);
    }

    public DynamicQuery() {
    }

    public DynamicQuery(CriteriaDefinition criteriaDefinition) {
        addCriteria(criteriaDefinition);
    }

    public DynamicQuery addCriteria(CriteriaDefinition criteriaDefinition) {
        CriteriaDefinition existing = this.criteria.get(criteriaDefinition.getKey());
        String key = criteriaDefinition.getKey();
        if (existing == null) {
            this.criteria.put(key, criteriaDefinition);
        } else {
            throw new InvalidDataAccessApiUsageException("Due to limitations of the data store you can not add ");
        }
        return this;
    }

    public int getLimit() {
        return limit;
    }

    public int getOffset() {
        return offset;
    }

    public DynamicQuery with(Pageable pageable) {
        if (pageable == null) {
            return this;
        }
        this.limit = pageable.getPageSize();
        this.offset = pageable.getOffset();
        return with(pageable.getSort());
    }

    public DynamicQuery limit(int limit) {
        this.limit = limit;
        return this;
    }

    public DynamicQuery with(Sort sort) {
        if (sort == null) {
            return this;
        }
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

    //TODO:Index is not yet implemented.
    public List<Criteria.ParameterBinding> getParameterBindings() {
        List<Criteria.ParameterBinding> parameterBindings = new LinkedList<>();
        for (CriteriaDefinition criteriaDefinition : criteria.values()) {
            parameterBindings = criteriaDefinition.getParameterBindings();
        }
        return parameterBindings;
    }

    public Criteria.ParameterBinding getIdParameterBinding() {
        for (CriteriaDefinition criteriaDefinition : criteria.values()) {
            return criteriaDefinition.getIdParameterBindings();
        }
        return null;
    }

    public List<CriteriaDefinition> getCriteria() {
        return new ArrayList<CriteriaDefinition>(this.criteria.values());
    }
}
