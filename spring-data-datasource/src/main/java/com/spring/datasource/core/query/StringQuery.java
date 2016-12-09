package com.spring.datasource.core.query;

import java.util.Map;

public interface StringQuery extends DataStoreQuery {

    String getQuery();

    String getOriginalQuery();

    Map<String, Object> getCriteria();
}
