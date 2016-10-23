package com.virgin.dao.mapping;

import com.virgin.dao.DataStoreTemplate;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.config.AbstractFactoryBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.data.mapping.model.PropertyNameFieldNamingStrategy;

public class DataStoreTemplateFactoryBean extends AbstractFactoryBean<DataStoreTemplate> implements
        ApplicationContextAware {

    private BeanFactory beanFactory;

    @Override
    public Class<?> getObjectType() {
        return DataStoreTemplate.class;
    }

    @Override
    protected DataStoreTemplate createInstance() throws Exception {
        DataStoreTemplate context = new DataStoreTemplate();
        return context;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.beanFactory = applicationContext;
    }
}
