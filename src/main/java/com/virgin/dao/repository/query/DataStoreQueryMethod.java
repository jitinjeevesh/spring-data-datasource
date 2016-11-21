package com.virgin.dao.repository.query;

import com.virgin.dao.mapping.DataStorePersistentEntity;
import com.virgin.dao.mapping.DataStorePersistentProperty;
import org.springframework.data.mapping.context.MappingContext;
import org.springframework.data.projection.ProjectionFactory;
import org.springframework.data.repository.core.RepositoryMetadata;
import org.springframework.data.repository.query.QueryMethod;
import org.springframework.util.ClassUtils;

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
     * @param factory  must not be {@literal null}.
     */
    public DataStoreQueryMethod(Method method, RepositoryMetadata metadata, ProjectionFactory factory, MappingContext<? extends DataStorePersistentEntity<?>, DataStorePersistentProperty> mappingContext) {
        super(method, metadata, factory);
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
}
