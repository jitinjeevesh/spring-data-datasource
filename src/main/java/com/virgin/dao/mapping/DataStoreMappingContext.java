package com.virgin.dao.mapping;

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

public class DataStoreMappingContext extends AbstractMappingContext<DataStorePersistentEntityImpl<?>, DataStorePersistentProperty>
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
    protected <T> DataStorePersistentEntityImpl<?> createPersistentEntity(TypeInformation<T> typeInformation) {
        return new DataStorePersistentEntityImpl<T>(typeInformation);
    }

    @Override
    protected DataStorePersistentProperty createPersistentProperty(Field field, PropertyDescriptor descriptor, DataStorePersistentEntityImpl<?> owner, SimpleTypeHolder simpleTypeHolder) {
        return new DataStorePersistentPropertyImpl(field, descriptor, owner, simpleTypeHolder, fieldNamingStrategy);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.context = applicationContext;
    }
}
