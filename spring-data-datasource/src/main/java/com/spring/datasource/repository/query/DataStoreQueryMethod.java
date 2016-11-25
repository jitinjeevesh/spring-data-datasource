package com.spring.datasource.repository.query;

import com.spring.datasource.mapping.DataStorePersistentEntity;
import com.spring.datasource.mapping.DataStorePersistentProperty;
import com.spring.datasource.repository.Query;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.data.mapping.context.MappingContext;
import org.springframework.data.projection.ProjectionFactory;
import org.springframework.data.repository.core.RepositoryMetadata;
import org.springframework.data.repository.query.QueryMethod;
import org.springframework.util.ClassUtils;
import org.springframework.util.StringUtils;

import java.lang.reflect.Method;

public class DataStoreQueryMethod extends QueryMethod {

    private final Method method;
    private DataStoreEntityMetaData<?> entityMetaData;
    private final MappingContext<? extends DataStorePersistentEntity<?>, DataStorePersistentProperty> mappingContext;

    /**
     * Creates a new {@link QueryMethod} from the given parameters. Looks up the correct query to use for following
     * invocations of the method given.
     *
     * @param method   must not be {@literal null}.
     * @param metadata must not be {@literal null}.
     */
    public DataStoreQueryMethod(Method method, RepositoryMetadata metadata,  MappingContext<? extends DataStorePersistentEntity<?>, DataStorePersistentProperty> mappingContext) {
        super(method, metadata);
        this.method = method;
        this.mappingContext = mappingContext;
    }

    @Override
    @SuppressWarnings("unchecked")
    public DataStoreEntityMetaData<?> getEntityInformation() {
        if (entityMetaData == null) {
            Class<?> returnedObjectType = getReturnedObjectType();
            Class<?> domainClass = getDomainClass();
            if (ClassUtils.isPrimitiveOrWrapper(returnedObjectType)) {
                this.entityMetaData = new DefaultDataStoreEntityMetaData<Object>((Class<Object>) domainClass, mappingContext.getPersistentEntity(domainClass));
            } else {
                DataStorePersistentEntity<?> returnedEntity = mappingContext.getPersistentEntity(returnedObjectType);
                DataStorePersistentEntity<?> managedEntity = mappingContext.getPersistentEntity(domainClass);
                returnedEntity = returnedEntity == null || returnedEntity.getType().isInterface() ? managedEntity : returnedEntity;
                DataStorePersistentEntity<?> collectionEntity = domainClass.isAssignableFrom(returnedObjectType) ? returnedEntity : managedEntity;
                this.entityMetaData = new DefaultDataStoreEntityMetaData<Object>((Class<Object>) returnedEntity.getType(), collectionEntity);
            }
        }
        return this.entityMetaData;
    }

    public boolean hasAnnotatedQuery() {
        return getAnnotatedQuery() != null;
    }

    public String getAnnotatedQuery() {
        String query = (String) AnnotationUtils.getValue(getQueryAnnotation());
        return StringUtils.hasText(query) ? query : null;
    }

    public Query getQueryAnnotation() {
        return AnnotatedElementUtils.findMergedAnnotation(method, Query.class);
    }

    String getFieldSpecification() {
        String value = (String) AnnotationUtils.getValue(getQueryAnnotation(), "fields");
        return StringUtils.hasText(value) ? value : null;
    }
}
