package com.virgin.dao.core.convert;

import com.google.cloud.datastore.Entity;
import com.google.cloud.datastore.Key;
import com.virgin.dao.mapping.DataStorePersistentEntity;
import com.virgin.dao.mapping.DataStorePersistentProperty;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.support.DefaultConversionService;
import org.springframework.data.convert.EntityInstantiator;
import org.springframework.data.mapping.PersistentPropertyAccessor;
import org.springframework.data.mapping.PropertyHandler;
import org.springframework.data.mapping.context.MappingContext;
import org.springframework.data.mapping.model.*;
import org.springframework.data.util.ClassTypeInformation;
import org.springframework.data.util.TypeInformation;
import org.springframework.util.Assert;

import java.lang.reflect.Constructor;

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
    public Object convertToDataStoreType(Object obj) {
        return null;
    }

    @Override
    public Object convertToDataStoreType(Object obj, TypeInformation<?> typeInformation) {
        return null;
    }

    @Override
    public MappingContext<? extends DataStorePersistentEntity<?>, DataStorePersistentProperty> getMappingContext() {
        return mappingContext;
    }

    @Override
    public ConversionService getConversionService() {
        return null;
    }

    @Override
    public <R extends Object> R read(Class<R> type, final Entity source) {
        R entity = null;
        try {
            Constructor<?> constructor = type.getConstructor();
            entity = (R) constructor.newInstance();
        } catch (Exception exp) {
            exp.printStackTrace();
        }
        TypeInformation<R> typeInformation = ClassTypeInformation.from(type);
        TypeInformation<? extends R> typeToUse = dataStoreTypeMapper.readType(source, typeInformation);

        Class<? extends R> rawType = typeToUse.getType();
        if (Entity.class.isAssignableFrom(rawType)) {
            System.out.println(source);
        }

        final DataStorePersistentEntity<R> persistentEntity = (DataStorePersistentEntity<R>) mappingContext.getPersistentEntity(typeToUse);

        final DefaultSpELExpressionEvaluator evaluator = new DefaultSpELExpressionEvaluator(source, spELContext);
//        EntityInstantiator instantiator = instantiators.getInstantiatorFor(persistentEntity);
//        R newInstance = instantiator.createInstance(persistentEntity, provider);
//        DataStorePropertyValueProvider provider = new DataStorePropertyValueProvider(source, evaluator);

        DataStorePropertyValueProvider provider = new DataStorePropertyValueProvider(source, evaluator);

        final PersistentPropertyAccessor accessor = new ConvertingPropertyAccessor(persistentEntity.getPropertyAccessor(entity), conversionService);


        System.out.println(".....................................>Accessor................................");
        System.out.println(accessor);
        final DataStorePersistentProperty idProperty = persistentEntity.getIdProperty();
        final R result = entity;
        System.out.println(".........................>Id property...................");
        System.out.println(idProperty);
        System.out.println(new DataStorePropertyValueProvider(source, evaluator).<Long>getPropertyValue(idProperty));
        // make sure id property is set before all other properties

        System.out.println("Creating id property.");
        Object idValue = null;


//        persistentEntity.doWithProperties(new FieldsNames(source, evaluator));
//        System.out.println("DataStorePersistentEntity");
//        System.out.println(persistentEntity);
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

                accessor.setProperty(prop, new DataStorePropertyValueProvider(source, evaluator).getPropertyValue(prop));
            }
        });

        System.out.println(result);
        return result;
    }

    @Override
    public void write(Object source, Entity sink) {

    }


    private class FieldsNames implements PropertyHandler<DataStorePersistentProperty> {

        private final SpELExpressionEvaluator evaluator;
        private final Entity entity;

        public FieldsNames(Entity entity, SpELExpressionEvaluator evaluator) {
            this.entity = entity;
            this.evaluator = evaluator;
        }

        @Override
        public void doWithPersistentProperty(DataStorePersistentProperty persistentProperty) {

            System.out.println("...................data store persistent property....................");
            System.out.println(persistentProperty.getFieldName());
            System.out.println(persistentProperty.getSetter());
            System.out.println(persistentProperty.getGetter());
            new DataStorePropertyValueProvider(entity, evaluator).getPropertyValue(persistentProperty);
        }
    }


    private class DataStorePropertyValueProvider implements PropertyValueProvider<DataStorePersistentProperty> {

        private final SpELExpressionEvaluator evaluator;
        //TODO:Instead of entity there is a class which is used for accessing and putting data in it.
        private final Entity source;

        public DataStorePropertyValueProvider(Entity source, SpELExpressionEvaluator evaluator) {

            Assert.notNull(source);
            Assert.notNull(evaluator);

            this.source = source;
            this.evaluator = evaluator;
        }

        public <T> T getPropertyValue(DataStorePersistentProperty property) {

            String expression = property.getSpelExpression();
            Object value = null;
            if (!property.isExplicitIdProperty()) {
                value = expression != null ? evaluator.evaluate(expression) : source.getValue(property.getFieldName()).get();
            } else {
                value = ((Key) source.key()).nameOrId();
            }
            if (value == null) {
                return null;
            }
            System.out.println(".........................>Inside class DataStorePropertyValueProvider.............................");
            System.out.println(value);
            System.out.println(value.getClass());
            System.out.println(conversionService.canConvert(value.getClass(), property.getType()));
            System.out.println("Property class type " + property.getType());
//            return conversionService.convert(value, (Class<T>) property.getType());
            return (T) value;
//            return readValue(value, property.getTypeInformation());
        }
    }
}
