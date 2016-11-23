package com.virgin.dao.core.query;

import com.virgin.dao.repository.query.DataStoreConvertingParameterAccessor;

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
