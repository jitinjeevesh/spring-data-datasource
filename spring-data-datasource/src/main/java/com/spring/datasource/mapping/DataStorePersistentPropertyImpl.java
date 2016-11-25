package com.spring.datasource.mapping;

import com.google.cloud.datastore.*;
import com.spring.datasource.core.convert.DataStoreMapper;
import com.spring.datasource.core.convert.DataStoreMapperFactory;
import com.spring.datasource.core.mapping.Column;
import com.spring.datasource.core.mapping.Embedded;
import com.spring.datasource.core.convert.DataStoreCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.annotation.Id;
import org.springframework.data.mapping.Association;
import org.springframework.data.mapping.PersistentEntity;
import org.springframework.data.mapping.model.*;
import org.springframework.util.StringUtils;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.*;

public class DataStorePersistentPropertyImpl extends AnnotationBasedPersistentProperty<DataStorePersistentProperty> implements
        DataStorePersistentProperty {

    private static final Logger LOG = LoggerFactory.getLogger(DataStorePersistentPropertyImpl.class);
    private static final String ID_FIELD_NAME = "id";
    private final FieldNamingStrategy fieldNamingStrategy;
    private static final Set<String> SUPPORTED_ID_PROPERTY_NAMES = new HashSet<String>();
    private final DataStoreMapperFactory dataStoreMapperFactory;
    private DataStoreCache<Type, Object> defaultNullValueCache = null;

    static {
        SUPPORTED_ID_PROPERTY_NAMES.add("id");
    }


    public DataStorePersistentPropertyImpl(Field field, PropertyDescriptor propertyDescriptor, PersistentEntity<?, DataStorePersistentProperty> owner, SimpleTypeHolder simpleTypeHolder, FieldNamingStrategy fieldNamingStrategy) {
        super(field, propertyDescriptor, owner, simpleTypeHolder);
        this.defaultNullValueCache = new DataStoreCache<>();
        populateDefaultNullValues();
        this.dataStoreMapperFactory = DataStoreMapperFactory.getInstance();
        this.fieldNamingStrategy = fieldNamingStrategy == null ? PropertyNameFieldNamingStrategy.INSTANCE : fieldNamingStrategy;
    }

    public DataStoreMapperFactory getDataStoreMapperFactory() {
        return dataStoreMapperFactory;
    }

    //TODO:We can define custom id implementation here. Right now we restrict only for @Id and id field name.
    @Override
    public boolean isIdProperty() {
        if (super.isIdProperty()) {
            return true;
        }
        return SUPPORTED_ID_PROPERTY_NAMES.contains(getName()) && !hasExplicitFieldName();

    }

    @Override
    protected Association<DataStorePersistentProperty> createAssociation() {
        return new Association<DataStorePersistentProperty>(this, null);
    }

    @Override
    public boolean isExplicitIdProperty() {
        return isAnnotationPresent(Id.class);
    }

    @Override
    public String getFieldName() {
        if (isIdProperty()) {
            if (owner == null) {
                return ID_FIELD_NAME;
            }

            if (owner.getIdProperty() == null) {
                return ID_FIELD_NAME;
            }

            if (owner.isIdProperty(this)) {
                return ID_FIELD_NAME;
            }
        }

        if (hasExplicitFieldName()) {
            return getAnnotatedFieldName();
        }

        String fieldName = fieldNamingStrategy.getFieldName(this);

        if (!StringUtils.hasText(fieldName)) {
            throw new MappingException(String.format("Invalid (null or empty) field name returned for property %s by %s!", this, fieldNamingStrategy.getClass()));
        }

        return fieldName;
    }

    @Override
    public DataStoreMapper getDataStoreMapper() {
        return null;
    }

    @Override
    public Object getConvertibleValue(Value<?> input) {
        if (input == null || input instanceof NullValue) {
            return defaultNullValueCache.get(getType());
        }
        DataStoreMapper dataStoreMapper = dataStoreMapperFactory.getMapper(this.getField().getGenericType());
        return dataStoreMapper.convert(input);
    }

    @Override
    public Value<?> getConvertibleValue(Object input) {
        if (input == null) {
            return NullValue.newBuilder().build();
        }
        DataStoreMapper dataStoreMapper = dataStoreMapperFactory.getMapper(this.getField().getGenericType());
        return dataStoreMapper.convert(input);
    }

    private boolean isEmbeddedField() {
        return isAnnotationPresent(Embedded.class);
    }

    @Override
    public Class<? extends Value<?>> getConvertibleType() {
        return dataStoreMapperFactory.getMapperClass(getType());
    }

    protected boolean hasExplicitFieldName() {
        return StringUtils.hasText(getAnnotatedFieldName());
    }

    private String getAnnotatedFieldName() {
        Column annotation = findAnnotation(Column.class);
        if (annotation != null && StringUtils.hasText(annotation.value())) {
            return annotation.value();
        }
        return null;
    }

    private void populateDefaultNullValues() {
        defaultNullValueCache.put(Boolean.class, false);
        defaultNullValueCache.put(boolean.class, false);
        defaultNullValueCache.put(List.class, new ArrayList<>());
        defaultNullValueCache.put(Set.class, new HashSet<>());
        defaultNullValueCache.put(Map.class, new HashMap<>());
    }
}
