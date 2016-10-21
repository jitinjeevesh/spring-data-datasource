package com.virgin.dao;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.data.mapping.context.AbstractMappingContext;
import org.springframework.data.mapping.model.FieldNamingStrategy;
import org.springframework.data.mapping.model.PropertyNameFieldNamingStrategy;
import org.springframework.data.mapping.model.SimpleTypeHolder;
import org.springframework.data.util.TypeInformation;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;

public class DataStoreMappingContext extends AbstractMappingContext<BasicDataStorePersistentEntity<?>, DataStorePersistentProperty>
        implements ApplicationContextAware {

    private static final FieldNamingStrategy DEFAULT_NAMING_STRATEGY = PropertyNameFieldNamingStrategy.INSTANCE;

    private FieldNamingStrategy fieldNamingStrategy = DEFAULT_NAMING_STRATEGY;
    private ApplicationContext context;

    public DataStoreMappingContext() {
    }

    public void setFieldNamingStrategy(FieldNamingStrategy fieldNamingStrategy) {
        this.fieldNamingStrategy = fieldNamingStrategy == null ? DEFAULT_NAMING_STRATEGY : fieldNamingStrategy;
    }

    @Override
    protected <T> BasicDataStorePersistentEntity<?> createPersistentEntity(TypeInformation<T> typeInformation) {
        BasicDataStorePersistentEntity<T> entity = new BasicDataStorePersistentEntity<T>(typeInformation);
        if (context != null) {
            entity.setApplicationContext(context);
        }
        return entity;
    }

    @Override
    protected DataStorePersistentProperty createPersistentProperty(Field field, PropertyDescriptor descriptor, BasicDataStorePersistentEntity<?> owner, SimpleTypeHolder simpleTypeHolder) {
        return new BasicDataStorePersistentProperty(field, descriptor, owner, simpleTypeHolder, fieldNamingStrategy);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.context = applicationContext;
    }
}
