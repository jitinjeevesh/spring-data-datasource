package com.spring.datasource.core.query;

import com.spring.datasource.repository.query.DataStoreConvertingParameterAccessor;

public class LiteralQuery implements StringQuery {

    private final String query;
    private final DataStoreConvertingParameterAccessor accessor;

    public LiteralQuery(String query, DataStoreConvertingParameterAccessor accessor) {
        this.query = query;
        this.accessor = accessor;
    }

    @Override
    public String getQuery() {
        return query;
    }
}
