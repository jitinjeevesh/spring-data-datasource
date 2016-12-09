package com.spring.datasource.core.query;

import com.google.cloud.datastore.StructuredQuery.Filter;

import java.util.List;

public interface CriteriaDefinition {

    String getKey();

    Object getValue();

    String getKeyWord();

    Filter getCriteriaFilter();

    List<Criteria.ParameterBinding> getParameterBindings();

    Criteria.ParameterBinding getIdParameterBindings();
}
