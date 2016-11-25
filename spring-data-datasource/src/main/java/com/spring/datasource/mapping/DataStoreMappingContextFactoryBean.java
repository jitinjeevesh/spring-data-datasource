package com.spring.datasource.mapping;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.config.AbstractFactoryBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.data.mapping.model.PropertyNameFieldNamingStrategy;

public class DataStoreMappingContextFactoryBean extends AbstractFactoryBean<DataStoreMappingContext> implements
        ApplicationContextAware {

    private BeanFactory beanFactory;

    @Override
    public Class<?> getObjectType() {
        return DataStoreMappingContext.class;
    }

    @Override
    protected DataStoreMappingContext createInstance() throws Exception {
        DataStoreMappingContext context = new DataStoreMappingContext();
        context.setFieldNamingStrategy(PropertyNameFieldNamingStrategy.INSTANCE);
        context.initialize();
        return context;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.beanFactory = applicationContext;
    }
}
