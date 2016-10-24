package com.virgin.dao.repository.query;

import com.virgin.dao.core.mapping.Kind;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

public class DefaultDataStoreEntityMetaData<T> implements DataStoreEntityMetaData<T> {

    private final Class<T> domainType;

    public DefaultDataStoreEntityMetaData(Class<T> domainType) {
        Assert.notNull(domainType, "Domain type must not be null!");
        this.domainType = domainType;
    }

    @Override
    public String getKindName() {
        Kind kind = AnnotatedElementUtils.findMergedAnnotation(domainType, Kind.class);
        boolean hasName = null != kind && StringUtils.hasText(kind.name());
        return hasName ? kind.name() : domainType.getSimpleName();
    }

    @Override
    public Class<T> getJavaType() {
        return domainType;
    }
}
