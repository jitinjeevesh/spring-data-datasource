package com.virgin.dao.repository.config;

import com.virgin.dao.DataStoreRepository;
import com.virgin.dao.Kind;
import com.virgin.dao.config.DataStoreTemplateParser;
import com.virgin.dao.mapping.DataStoreMappingContextFactoryBean;
import com.virgin.dao.repository.support.DataStoreRepositoryFactoryBean;
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

    private static final String DATASTORE_TEMPLATE_REF = "datastore-template-ref";

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
    }

    @Override
    public void postProcess(BeanDefinitionBuilder builder, AnnotationRepositoryConfigurationSource config) {
        AnnotationAttributes attributes = config.getAttributes();
        System.out.println("..........................................................................");
        System.out.println(attributes);
        String dataStoreTemplateRef = attributes.getString("datastoreTemplateRef");
        if (StringUtils.hasText(dataStoreTemplateRef))
            builder.addPropertyReference("dataStoreEntityManager", attributes.getString("datastoreTemplateRef"));
    }


    @Override
    public void registerBeansForRoot(BeanDefinitionRegistry registry, RepositoryConfigurationSource config) {

        super.registerBeansForRoot(registry, config);

        Object source = config.getSource();
        registerIfNotAlreadyRegistered(new RootBeanDefinition(DataStoreMappingContextFactoryBean.class), registry,
                "dataStoreMappingContext", source);

        registerIfNotAlreadyRegistered(new RootBeanDefinition(DataStoreTemplateParser.class), registry,
                "datastoreTemplateRef", source);

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
  /*  @Override
    public void postProcess(BeanDefinitionBuilder builder, RepositoryConfigurationSource source) {

        builder.addPropertyValue("dataStoreEntityManager", getEntityManagerBeanDefinitionFor(source, source.getSource()));
        builder.addPropertyReference("mappingContext", JPA_MAPPING_CONTEXT_BEAN_NAME);
    }*/

   /* private static AbstractBeanDefinition getEntityManagerBeanDefinitionFor(RepositoryConfigurationSource config,
                                                                            Object source) {

        BeanDefinitionBuilder builder = BeanDefinitionBuilder
                .rootBeanDefinition("org.springframework.orm.jpa.SharedEntityManagerCreator");
        builder.setFactoryMethod("createSharedEntityManager");
        builder.addConstructorArgReference(getEntityManagerBeanRef(config));

        AbstractBeanDefinition bean = builder.getRawBeanDefinition();
        bean.setSource(source);

        return bean;
    }

    private static String getEntityManagerBeanRef(RepositoryConfigurationSource config) {

        String entityManagerFactoryRef = config == null ? null : config.getAttribute("entityManagerFactoryRef");
        return entityManagerFactoryRef == null ? "entityManagerFactory" : entityManagerFactoryRef;
    }*/

   /* @Override
    public void registerBeansForRoot(BeanDefinitionRegistry registry, RepositoryConfigurationSource config) {

        super.registerBeansForRoot(registry, config);

        Object source = config.getSource();

        registerIfNotAlreadyRegistered(new RootBeanDefinition(EntityManagerBeanDefinitionRegistrarPostProcessor.class),
                registry, EM_BEAN_DEFINITION_REGISTRAR_POST_PROCESSOR_BEAN_NAME, source);

        registerIfNotAlreadyRegistered(new RootBeanDefinition(JpaMetamodelMappingContextFactoryBean.class), registry,
                JPA_MAPPING_CONTEXT_BEAN_NAME, source);

        registerIfNotAlreadyRegistered(new RootBeanDefinition(PAB_POST_PROCESSOR), registry,
                AnnotationConfigUtils.PERSISTENCE_ANNOTATION_PROCESSOR_BEAN_NAME, source);

        // Register bean definition for DefaultJpaContext

        RootBeanDefinition contextDefinition = new RootBeanDefinition(DefaultJpaContext.class);
        contextDefinition.setAutowireMode(AbstractBeanDefinition.AUTOWIRE_CONSTRUCTOR);

        registerIfNotAlreadyRegistered(contextDefinition, registry, JPA_CONTEXT_BEAN_NAME, source);
    }*/
}
