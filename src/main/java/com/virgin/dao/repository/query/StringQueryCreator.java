package com.virgin.dao.repository.query;

import com.virgin.dao.core.query.LiteralQuery;
import com.virgin.dao.core.query.NamedBindingQuery;
import com.virgin.dao.core.query.StringQuery;

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
