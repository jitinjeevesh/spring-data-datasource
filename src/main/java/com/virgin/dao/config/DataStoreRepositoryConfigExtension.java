package com.virgin.dao.config;

import com.virgin.dao.DataStoreRepository;
import com.virgin.dao.DataStoreRepositoryFactoryBean;
import com.virgin.dao.Kind;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.data.config.ParsingUtils;
import org.springframework.data.repository.config.RepositoryConfigurationExtensionSupport;
import org.springframework.data.repository.config.XmlRepositoryConfigurationSource;
import org.w3c.dom.Element;

import java.lang.annotation.Annotation;
import java.util.Collection;
import java.util.Collections;

public class DataStoreRepositoryConfigExtension extends RepositoryConfigurationExtensionSupport {

    private static final String DATASTORE_TEMPLATE_REF = "mongo-template-ref";

    @Override
    public String getModuleName() {
        return "DataStore";
    }

    @Override
    protected String getModulePrefix() {
        return "datastore";
    }

    @Override
    public String getRepositoryFactoryClassName() {
        return DataStoreRepositoryFactoryBean.class.getName();
    }

    @Override
    protected Collection<Class<? extends Annotation>> getIdentifyingAnnotations() {
        return Collections.<Class<? extends Annotation>>singleton(Kind.class);
    }

    @Override
    protected Collection<Class<?>> getIdentifyingTypes() {
        return Collections.<Class<?>>singleton(DataStoreRepository.class);
    }

    @Override
    public void postProcess(BeanDefinitionBuilder builder, XmlRepositoryConfigurationSource config) {

        Element element = config.getElement();

        ParsingUtils.setPropertyReference(builder, element, DATASTORE_TEMPLATE_REF, "dataStoreEntityManager");
//        ParsingUtils.setPropertyValue(builder, element, CREATE_QUERY_INDEXES, "createIndexesForQueryMethods");
    }
}
