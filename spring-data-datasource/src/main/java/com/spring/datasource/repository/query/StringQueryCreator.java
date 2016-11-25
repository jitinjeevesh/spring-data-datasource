package com.spring.datasource.repository.query;

import com.spring.datasource.core.query.LiteralQuery;
import com.spring.datasource.core.query.NamedBindingQuery;
import com.spring.datasource.core.query.StringQuery;

public class StringQueryCreator {

    private final DataStoreConvertingParameterAccessor accessor;
    private final String query;
    private final boolean isAllowLiteral;

    public StringQueryCreator(String query, DataStoreConvertingParameterAccessor accessor, boolean isAllowLiteral) {
        this.query = query;
        this.accessor = accessor;
        this.isAllowLiteral = isAllowLiteral;
    }

    StringQuery createQuery() {
        if (isAllowLiteral) {
            return new LiteralQuery(query, accessor);
        } else {
            return new NamedBindingQuery(query, accessor);
        }
    }
}
