package com.virgin.dao.repository.config;

import com.virgin.dao.core.DefaultDataStoreEntityManager;
import com.virgin.dao.core.converter.MappingDataStoreConverter;
import com.virgin.dao.repository.DataStoreRepository;
import com.virgin.dao.core.DataStoreTemplate;
import com.virgin.dao.core.mapping.Kind;
import com.virgin.dao.mapping.DataStoreMappingContextFactoryBean;
import com.virgin.dao.repository.support.DataStoreRepositoryFactoryBean;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.factory.config.ConstructorArgumentValues;
import org.springframework.beans.factory.config.RuntimeBeanReference;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.data.config.ParsingUtils;
import org.springframework.data.repository.config.AnnotationRepositoryConfigurationSource;
import org.springframework.data.repository.config.RepositoryConfigurationExtensionSupport;
import org.springframework.data.repository.config.RepositoryConfigurationSource;
import org.springframework.data.repository.config.XmlRepositoryConfigurationSource;
import org.springframework.util.StringUtils;
import org.w3c.dom.Element;

import java.lang.annotation.Annotation;
import java.util.Collection;
import java.util.Collections;

public class DataStoreRepositoryConfigExtension extends RepositoryConfigurationExtensionSupport {

    private static final String DATASTORE_TEMPLATE_REF = "dataStore-template-ref";

    @Override
    public String getModuleName() {
        return "DataStore";
    }

    @Override
    protected String getModulePrefix() {
        return "dataStore";
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
        ParsingUtils.setPropertyReference(builder, element, DATASTORE_TEMPLATE_REF, "dataStoreOperation");
    }

    @Override
    public void postProcess(BeanDefinitionBuilder builder, AnnotationRepositoryConfigurationSource config) {
        AnnotationAttributes attributes = config.getAttributes();
        System.out.println("..........................................................................");
        System.out.println(attributes);
        String dataStoreTemplateRef = attributes.getString("dataStoreTemplateRef");
        if (StringUtils.hasText(dataStoreTemplateRef))
            builder.addPropertyReference("dataStoreOperation", attributes.getString("dataStoreTemplateRef"));
    }


    @Override
    public void registerBeansForRoot(BeanDefinitionRegistry registry, RepositoryConfigurationSource config) {

        super.registerBeansForRoot(registry, config);

        System.out.println("............................registerBeansForRoot.......................................");
        Object source = config.getSource();
//        registerIfNotAlreadyRegistered(new RootBeanDefinition(DataStoreTemplateFactoryBean.class), registry,
//                "dataStoreTemplate", source);

        registerIfNotAlreadyRegistered(new RootBeanDefinition(DataStoreMappingContextFactoryBean.class), registry,
                "dataStoreMappingContext", source);


        //This is MappingDataStoreConverter bean definition.
        RootBeanDefinition mappingDataStoreConverterBeanDefinition = new RootBeanDefinition(MappingDataStoreConverter.class);
        mappingDataStoreConverterBeanDefinition.setAutowireMode(AbstractBeanDefinition.AUTOWIRE_CONSTRUCTOR);
        ConstructorArgumentValues mappingDataStoreConverterConstructorArgumentValues = new ConstructorArgumentValues();
        mappingDataStoreConverterConstructorArgumentValues.addIndexedArgumentValue(0, registry.getBeanDefinition("dataStoreMappingContext"));
        mappingDataStoreConverterBeanDefinition.setConstructorArgumentValues(mappingDataStoreConverterConstructorArgumentValues);
        registerIfNotAlreadyRegistered(mappingDataStoreConverterBeanDefinition, registry, "mappingDataStoreConverter", source);


        //This is defaultDataStoreEntityManager bean definition.
        RootBeanDefinition defaultDataStoreEntityManagerBeanDefinition = new RootBeanDefinition(DefaultDataStoreEntityManager.class);
        defaultDataStoreEntityManagerBeanDefinition.setAutowireMode(AbstractBeanDefinition.AUTOWIRE_CONSTRUCTOR);
        registerIfNotAlreadyRegistered(defaultDataStoreEntityManagerBeanDefinition, registry, "defaultDataStoreEntityManager", source);


        //This is datastore template bean definition.
        RootBeanDefinition rootBeanDefinition = new RootBeanDefinition(DataStoreTemplate.class);
        rootBeanDefinition.setAutowireMode(AbstractBeanDefinition.AUTOWIRE_CONSTRUCTOR);
        ConstructorArgumentValues constructorArgumentValues = new ConstructorArgumentValues();
        constructorArgumentValues.addIndexedArgumentValue(0, registry.getBeanDefinition("defaultDataStoreEntityManager"));
        constructorArgumentValues.addIndexedArgumentValue(1, registry.getBeanDefinition("mappingDataStoreConverter"));
        rootBeanDefinition.setConstructorArgumentValues(constructorArgumentValues);
        registerIfNotAlreadyRegistered(rootBeanDefinition, registry, "dataStoreTemplate", source);


//        registerIfNotAlreadyRegistered(new RootBeanDefinition(DataStoreTemplateParser.class), registry,
//                "dataStoreTemplateRef", source);

       /* registerIfNotAlreadyRegistered(new RootBeanDefinition(EntityManagerBeanDefinitionRegistrarPostProcessor.class),
                registry, EM_BEAN_DEFINITION_REGISTRAR_POST_PROCESSOR_BEAN_NAME, source);

        registerIfNotAlreadyRegistered(new RootBeanDefinition(JpaMetamodelMappingContextFactoryBean.class), registry,
                JPA_MAPPING_CONTEXT_BEAN_NAME, source);

        registerIfNotAlreadyRegistered(new RootBeanDefinition(PAB_POST_PROCESSOR), registry,
                AnnotationConfigUtils.PERSISTENCE_ANNOTATION_PROCESSOR_BEAN_NAME, source);

        // Register bean definition for DefaultJpaContext

        RootBeanDefinition contextDefinition = new RootBeanDefinition(DefaultJpaContext.class);
        contextDefinition.setAutowireMode(AbstractBeanDefinition.AUTOWIRE_CONSTRUCTOR);

        registerIfNotAlreadyRegistered(contextDefinition, registry, JPA_CONTEXT_BEAN_NAME, source);*/
    }
}
