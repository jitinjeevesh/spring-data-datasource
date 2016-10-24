package com.virgin.dao.config;

import com.google.cloud.datastore.Datastore;
import com.google.cloud.datastore.DatastoreOptions;
import com.virgin.dao.core.DataStoreTemplate;
import com.virgin.dao.core.mapping.Kind;
import com.virgin.dao.core.converter.MappingDataStoreConverter;
import com.virgin.dao.mapping.DataStoreMappingContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.data.annotation.Persistent;
import org.springframework.data.mapping.model.FieldNamingStrategy;
import org.springframework.data.mapping.model.PropertyNameFieldNamingStrategy;
import org.springframework.stereotype.Component;
import org.springframework.util.ClassUtils;
import org.springframework.util.StringUtils;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Component
public abstract class AbstractDataStoreConfiguration {

    private static final Logger LOG = LoggerFactory.getLogger(AbstractDataStoreConfiguration.class);

    @Bean
    public MappingDataStoreConverter mappingDataStoreConverter() throws Exception {
        LOG.info("Creating Bean for : MappingDataStoreConverter");
        return new MappingDataStoreConverter(dataStoreMappingContext());
    }

    //TODO:Move this dataStore to different class
    /*@Bean
    public DataStoreTemplate dataStoreTemplate() throws Exception {
        LOG.info("Creating Bean for : DefaultDataStoreEntityManager");
        Datastore datastore = DatastoreOptions.defaultInstance().service();
        return new DataStoreTemplate(datastore, mappingDataStoreConverter());
    }*/

    @Bean
    public DataStoreMappingContext dataStoreMappingContext() throws ClassNotFoundException {
        LOG.info("Creating Bean for : DataStoreMappingContext");
        System.out.println("..........................................................................");
        System.out.println(getInitialEntitySet());
        DataStoreMappingContext mappingContext = new DataStoreMappingContext();
        mappingContext.setInitialEntitySet(getInitialEntitySet());
//        mappingContext.setSimpleTypeHolder(customConversions().getSimpleTypeHolder());
        mappingContext.setFieldNamingStrategy(fieldNamingStrategy());

        return mappingContext;
    }

/*    @Bean
    public MongoTemplate mongoTemplate() throws Exception {
        return new MongoTemplate(mongoDbFactory(), mappingMongoConverter());
    }*/

    protected Set<Class<?>> getInitialEntitySet() throws ClassNotFoundException {
        Set<Class<?>> initialEntitySet = new HashSet<Class<?>>();
        for (String basePackage : getMappingBasePackages()) {
            initialEntitySet.addAll(scanForEntities(basePackage));
        }
        return initialEntitySet;
    }

    protected Collection<String> getMappingBasePackages() {
        return Collections.singleton(getMappingBasePackage());
    }

    protected String getMappingBasePackage() {
        Package mappingBasePackage = getClass().getPackage();
        return mappingBasePackage == null ? null : mappingBasePackage.getName();
    }

    protected Set<Class<?>> scanForEntities(String basePackage) throws ClassNotFoundException {
        if (!StringUtils.hasText(basePackage)) {
            return Collections.emptySet();
        }
        Set<Class<?>> initialEntitySet = new HashSet<Class<?>>();

        if (StringUtils.hasText(basePackage)) {

            ClassPathScanningCandidateComponentProvider componentProvider = new ClassPathScanningCandidateComponentProvider(false);
            componentProvider.addIncludeFilter(new AnnotationTypeFilter(Kind.class));
            componentProvider.addIncludeFilter(new AnnotationTypeFilter(Persistent.class));

            for (BeanDefinition candidate : componentProvider.findCandidateComponents(basePackage)) {
                initialEntitySet.add(ClassUtils.forName(candidate.getBeanClassName(), AbstractDataStoreConfiguration.class.getClassLoader()));
            }
        }

        return initialEntitySet;
    }

    protected boolean abbreviateFieldNames() {
        return false;
    }

    //TODO:If camel case want
    protected FieldNamingStrategy fieldNamingStrategy() {
//        return abbreviateFieldNames() ? new CamelCaseAbbreviatingFieldNamingStrategy() : PropertyNameFieldNamingStrategy.INSTANCE;
        return PropertyNameFieldNamingStrategy.INSTANCE;
    }
}
