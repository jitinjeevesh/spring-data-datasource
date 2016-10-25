package com.virgin.dao.mapping;

import com.virgin.dao.core.mapping.Column;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.annotation.Id;
import org.springframework.data.mapping.Association;
import org.springframework.data.mapping.PersistentEntity;
import org.springframework.data.mapping.model.*;
import org.springframework.util.StringUtils;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Set;

public class DataStorePersistentPropertyImpl extends AnnotationBasedPersistentProperty<DataStorePersistentProperty> implements
        DataStorePersistentProperty {

    private static final Logger LOG = LoggerFactory.getLogger(DataStorePersistentPropertyImpl.class);
    private static final String ID_FIELD_NAME = "id";
    private final FieldNamingStrategy fieldNamingStrategy;
    private static final Set<String> SUPPORTED_ID_PROPERTY_NAMES = new HashSet<String>();

    static {
        SUPPORTED_ID_PROPERTY_NAMES.add("id");
    }


    public DataStorePersistentPropertyImpl(Field field, PropertyDescriptor propertyDescriptor, PersistentEntity<?, DataStorePersistentProperty> owner, SimpleTypeHolder simpleTypeHolder, FieldNamingStrategy fieldNamingStrategy) {
        super(field, propertyDescriptor, owner, simpleTypeHolder);
        this.fieldNamingStrategy = fieldNamingStrategy == null ? PropertyNameFieldNamingStrategy.INSTANCE : fieldNamingStrategy;
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
}
