package com.spring.datasource.core.convert;

import com.google.cloud.datastore.*;
import com.spring.datasource.core.query.DynamicQuery;
import com.spring.datasource.core.query.Criteria.ParameterBinding;
import com.spring.datasource.mapping.DataStorePersistentEntity;
import com.spring.datasource.mapping.DataStorePersistentProperty;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.support.DefaultConversionService;
import org.springframework.data.mapping.PersistentPropertyAccessor;
import org.springframework.data.mapping.PropertyHandler;
import org.springframework.data.mapping.context.MappingContext;
import org.springframework.data.mapping.model.*;
import org.springframework.data.util.ClassTypeInformation;
import org.springframework.data.util.TypeInformation;
import org.springframework.util.Assert;

public class MappingDataStoreConverter extends AbstractDataStoreConverter {


    protected final MappingContext<? extends DataStorePersistentEntity<?>, DataStorePersistentProperty> mappingContext;
    protected DataStoreTypeMapper dataStoreTypeMapper;

    private SpELContext spELContext;

    public MappingDataStoreConverter(MappingContext<? extends DataStorePersistentEntity<?>, DataStorePersistentProperty> mappingContext) {
        super(new DefaultConversionService());
        this.mappingContext = mappingContext;
        this.dataStoreTypeMapper = new DataStoreTypeMapperImpl(DataStoreTypeMapperImpl.DEFAULT_TYPE_KEY, mappingContext);
        this.spELContext = new SpELContext(DataStoreEntityPropertyAccessor.INSTANCE);
    }

    @Override
    public MappingContext<? extends DataStorePersistentEntity<?>, DataStorePersistentProperty> getMappingContext() {
        return mappingContext;
    }

    @Override
    public Object convertToDataStoreType(Object obj) {
        return null;
    }

    //TODO:Implement this method for all the data types.
    @Override
    public Object convertToDataStoreType(Object obj, TypeInformation<?> typeInformation) {
        if (obj == null) {
            return NullValue.newBuilder().build();
        }
        Class<?> target = dataStoreMapperFactory.getMapperClass(obj.getClass());
        if (target != null) {
            return conversionService.convert(obj, target);
        }
        return null;
    }

    //TODO:Refactor this method
    @Override
    public BaseEntity<?> convertToDataStoreType(Object obj, Key key) {
        final BaseEntity.Builder<?, ?> entityBuilder = Entity.newBuilder(key);
        Class<?> entityType = obj.getClass();
        TypeInformation<?> type = ClassTypeInformation.from(entityType);
        DataStorePersistentEntity<?> dataStorePersistentEntity = mappingContext.getPersistentEntity(entityType);
        final PersistentPropertyAccessor accessor = dataStorePersistentEntity.getPropertyAccessor(obj);
        final DataStorePersistentProperty idProperty = dataStorePersistentEntity.getIdProperty();
        dataStorePersistentEntity.doWithProperties(new PropertyHandler<DataStorePersistentProperty>() {
            public void doWithPersistentProperty(DataStorePersistentProperty prop) {
                if (prop.equals(idProperty) || !prop.isWritable()) {
                    return;
                }
                Object propertyObj = accessor.getProperty(prop);
                Value<?> datastoreValue = prop.getConvertibleValue(propertyObj);
                entityBuilder.set(prop.getFieldName(), datastoreValue);
            }
        });
        BaseEntity<?> baseEntity = entityBuilder.build();
        return baseEntity;
    }

    @Override
    public BaseEntity<?> convertToDataStoreType(final Object obj, KeyFactory keyFactory) {
        Object idValue = getIdValue(obj.getClass(), obj);
        Key key = getKey(idValue, keyFactory);
        final BaseEntity.Builder<?, ?> entityBuilder = Entity.newBuilder(key);
        Class<?> entityType = obj.getClass();
        TypeInformation<?> type = ClassTypeInformation.from(entityType);
        DataStorePersistentEntity<?> dataStorePersistentEntity = mappingContext.getPersistentEntity(entityType);
        final PersistentPropertyAccessor accessor = dataStorePersistentEntity.getPropertyAccessor(obj);
        final DataStorePersistentProperty idProperty = dataStorePersistentEntity.getIdProperty();
        dataStorePersistentEntity.doWithProperties(new PropertyHandler<DataStorePersistentProperty>() {
            public void doWithPersistentProperty(DataStorePersistentProperty prop) {
                if (prop.equals(idProperty) || !prop.isWritable()) {
                    return;
                }
                Object propertyObj = accessor.getProperty(prop);
                Value<?> datastoreValue = prop.getConvertibleValue(propertyObj);
                entityBuilder.set(prop.getFieldName(), datastoreValue);
            }
        });
        return entityBuilder.build();
    }

    @Override
    public BaseEntity<?> convertToDataStoreType(DynamicQuery bindings, Key key, Entity oldEntity) {
        final BaseEntity.Builder<?, ?> entityBuilder = Entity.newBuilder(key, oldEntity);
        for (ParameterBinding parameterBinding : bindings.getParameterBindings()) {
            entityBuilder.set(parameterBinding.getName(), dataStoreMapperFactory.getMapperValue(parameterBinding.getValue()));
        }
        return entityBuilder.build();
    }

    public Key getKey(Object idValue, KeyFactory keyFactory) {
        Key key = null;
        if (idValue instanceof Long) {
            key = keyFactory.newKey((Long) idValue);
        } else {
            key = keyFactory.newKey((String) idValue);
        }
        return key;
    }

    @Override
    public ConversionService getConversionService() {
        return this.conversionService;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <R extends Object> R read(Class<R> type, final Entity source) {
        R entity = (R) DataStoreUtil.instantiateObject(type);
        TypeInformation<R> typeInformation = ClassTypeInformation.from(type);
        TypeInformation<? extends R> typeToUse = dataStoreTypeMapper.readType(source, typeInformation);

        Class<? extends R> rawType = typeToUse.getType();
        if (Entity.class.isAssignableFrom(rawType)) {
            System.out.println(source);
        }
        final DataStorePersistentEntity<R> persistentEntity = (DataStorePersistentEntity<R>) mappingContext.getPersistentEntity(typeToUse);
        final DefaultSpELExpressionEvaluator evaluator = new DefaultSpELExpressionEvaluator(source, spELContext);

        //TODO:Needs POC on this.Used by spring data to instantiate entity.
//        ParameterValueProvider<DataStorePersistentProperty> provider1 = getParameterProvider(entity, dbo, evaluator, path);
//        EntityInstantiator instantiator = instantiators.getInstantiatorFor(persistentEntity);
//        R newInstance = instantiator.createInstance(persistentEntity, provider);
//        DataStorePropertyValueProvider provider = new DataStorePropertyValueProvider(source, evaluator);

        final PersistentPropertyAccessor accessor = new ConvertingPropertyAccessor(persistentEntity.getPropertyAccessor(entity), conversionService);
        final DataStorePersistentProperty idProperty = persistentEntity.getIdProperty();
        final R result = entity;
        // make sure id property is set before all other properties
        Object idValue = null;
        if (idProperty != null) {
            idValue = new DataStorePropertyValueProvider(source, evaluator).getPropertyValue(idProperty);
            accessor.setProperty(idProperty, idValue);
        }
        persistentEntity.doWithProperties(new PropertyHandler<DataStorePersistentProperty>() {
            public void doWithPersistentProperty(DataStorePersistentProperty prop) {

                // we skip the id property since it was already set
                if (idProperty != null && idProperty.equals(prop)) {
                    return;
                }

                if (persistentEntity.isConstructorArgument(prop)) {
                    return;
                }
                Object value = new DataStorePropertyValueProvider(source, evaluator).getPropertyValue(prop);
                System.out.println(".................................Property.............................");
                System.out.println(value);
                Object convertedValue = prop.getConvertibleValue((Value<?>) value);
                accessor.setProperty(prop, convertedValue);
            }
        });
        return result;
    }

    @Override
    public Object getIdValue(Class<?> type, Object obj) {
        DataStorePersistentEntity<?> dataStorePersistentEntity = mappingContext.getPersistentEntity(type);
        PersistentPropertyAccessor accessor = dataStorePersistentEntity.getPropertyAccessor(obj);
        DataStorePersistentProperty idProperty = dataStorePersistentEntity.getIdProperty();
        return accessor.getProperty(idProperty);
    }

    @Override
    public void write(Object source, Entity sink) {

    }


    private class DataStoreFieldsNames implements PropertyHandler<DataStorePersistentProperty> {

        private final SpELExpressionEvaluator evaluator;
        private final Entity entity;

        public DataStoreFieldsNames(Entity entity, SpELExpressionEvaluator evaluator) {
            this.entity = entity;
            this.evaluator = evaluator;
        }

        @Override
        public void doWithPersistentProperty(DataStorePersistentProperty persistentProperty) {
            new DataStorePropertyValueProvider(entity, evaluator).getPropertyValue(persistentProperty);
        }
    }


    private class DataStorePropertyValueProvider implements PropertyValueProvider<DataStorePersistentProperty> {

        private final SpELExpressionEvaluator evaluator;
        private final DataStoreEntityAccessor entityAccessor;
        private final Entity source;

        public DataStorePropertyValueProvider(Entity source, SpELExpressionEvaluator evaluator) {

            Assert.notNull(source);
            Assert.notNull(evaluator);

            this.source = source;
            this.evaluator = evaluator;
            this.entityAccessor = new DataStoreEntityAccessor(source);
        }

        public <T> T getPropertyValue(DataStorePersistentProperty property) {
            String expression = property.getSpelExpression();
            Object value = null;
            if (!property.isExplicitIdProperty()) {
                value = expression != null ? evaluator.evaluate(expression) : entityAccessor.get(property);
            } else {
                value = ((Key) source.key()).nameOrId();
            }
            return (T) value;
        }
    }
}
