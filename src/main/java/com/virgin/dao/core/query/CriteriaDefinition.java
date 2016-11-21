package com.virgin.dao.core.query;

import com.google.cloud.datastore.StructuredQuery.Filter;

public interface CriteriaDefinition {

    String getKey();

    Filter getCriteriaFilter();
}
