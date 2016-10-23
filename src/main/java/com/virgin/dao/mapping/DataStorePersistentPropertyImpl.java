package com.virgin.dao.mapping;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mapping.Association;
import org.springframework.data.mapping.PersistentEntity;
import org.springframework.data.mapping.model.*;
import org.springframework.util.StringUtils;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;

public class DataStorePersistentPropertyImpl extends AnnotationBasedPersistentProperty<DataStorePersistentProperty> implements
        DataStorePersistentProperty {

    private static final Logger LOG = LoggerFactory.getLogger(DataStorePersistentPropertyImpl.class);
    private static final String ID_FIELD_NAME = "id";
    private final FieldNamingStrategy fieldNamingStrategy;


    public DataStorePersistentPropertyImpl(Field field, PropertyDescriptor propertyDescriptor, PersistentEntity<?, DataStorePersistentProperty> owner, SimpleTypeHolder simpleTypeHolder, FieldNamingStrategy fieldNamingStrategy) {
        super(field, propertyDescriptor, owner, simpleTypeHolder);
        this.fieldNamingStrategy = fieldNamingStrategy == null ? PropertyNameFieldNamingStrategy.INSTANCE
                : fieldNamingStrategy;

      /*  if (isIdProperty() && getFieldName() != ID_FIELD_NAME) {
            LOG.warn("Customizing field name for id property not allowed! Custom name will not be considered!");
        }*/
    }

    @Override
    protected Association<DataStorePersistentProperty> createAssociation() {
        return new Association<DataStorePersistentProperty>(this, null);
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

        //Todo: Custom field name annotation.
        /*if (hasExplicitFieldName()) {
            return getAnnotatedFieldName();
        }*/

        String fieldName = fieldNamingStrategy.getFieldName(this);

        if (!StringUtils.hasText(fieldName)) {
            throw new MappingException(String.format("Invalid (null or empty) field name returned for property %s by %s!",
                    this, fieldNamingStrategy.getClass()));
        }

        return fieldName;
    }
}
