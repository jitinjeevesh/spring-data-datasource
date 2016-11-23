package com.virgin.dao.core.query;

import com.google.cloud.datastore.StructuredQuery.Filter;

import java.util.List;

public interface CriteriaDefinition {

    String getKey();

    Object getValue();

    Filter getCriteriaFilter();

    List<Criteria.ParameterBinding> getParameterBindings();

    Criteria.ParameterBinding getIdParameterBindings();
}
