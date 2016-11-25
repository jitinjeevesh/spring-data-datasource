package com.spring.datasource.repository.query;

import com.spring.datasource.core.mapping.Kind;
import com.spring.datasource.mapping.DataStorePersistentEntity;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

public class DefaultDataStoreEntityMetaData<T> implements DataStoreEntityMetaData<T> {

    private final Class<T> domainType;
    protected final DataStorePersistentEntity<?> dataStorePersistentEntity;

    public DefaultDataStoreEntityMetaData(Class<T> domainType, DataStorePersistentEntity<?> dataStorePersistentEntity) {
        Assert.notNull(domainType, "Domain type must not be null!");
        this.domainType = domainType;
        this.dataStorePersistentEntity = dataStorePersistentEntity;
    }

    @Override
    public String getKindName() {
        if (dataStorePersistentEntity != null) {
            return dataStorePersistentEntity.getKind();
        } else {
            Kind kind = AnnotatedElementUtils.findMergedAnnotation(domainType, Kind.class);
            boolean hasName = null != kind && StringUtils.hasText(kind.name());
            return hasName ? kind.name() : domainType.getSimpleName();
        }
    }

    @Override
    public Class<T> getJavaType() {
        return domainType;
    }
}
