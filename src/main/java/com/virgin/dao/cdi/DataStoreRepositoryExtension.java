package com.virgin.dao.cdi;

import com.virgin.dao.DataStoreEntityManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.repository.cdi.CdiRepositoryBean;
import org.springframework.data.repository.cdi.CdiRepositoryExtensionSupport;

import javax.enterprise.event.Observes;
import javax.enterprise.inject.UnsatisfiedResolutionException;
import javax.enterprise.inject.spi.AfterBeanDiscovery;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.BeanManager;
import javax.enterprise.inject.spi.ProcessBean;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class DataStoreRepositoryExtension extends CdiRepositoryExtensionSupport {

    private static final Logger LOG = LoggerFactory.getLogger(DataStoreRepositoryExtension.class);
    private final Map<Set<Annotation>, Bean<DataStoreEntityManager>> dataStoreEntityManager = new HashMap<Set<Annotation>, Bean<DataStoreEntityManager>>();

    public DataStoreRepositoryExtension() {
        LOG.info("Activating CDI extension for Spring Data DataStore repositories.");
    }

    <X> void processBean(@Observes ProcessBean<X> processBean) {

        Bean<X> bean = processBean.getBean();

        for (Type type : bean.getTypes()) {
            if (type instanceof Class<?> && DataStoreEntityManager.class.isAssignableFrom((Class<?>) type)) {
                if (LOG.isDebugEnabled()) {
                    LOG.debug(String.format("Discovered %s with qualifiers %s.", DataStoreEntityManager.class.getName(),
                            bean.getQualifiers()));
                }
                // Store the EntityManager bean using its qualifiers.
                dataStoreEntityManager.put(new HashSet<Annotation>(bean.getQualifiers()), (Bean<DataStoreEntityManager>) bean);
            }
        }
    }

    void afterBeanDiscovery(@Observes AfterBeanDiscovery afterBeanDiscovery, BeanManager beanManager) {
        for (Map.Entry<Class<?>, Set<Annotation>> entry : getRepositoryTypes()) {
            Class<?> repositoryType = entry.getKey();
            Set<Annotation> qualifiers = entry.getValue();

            // Create the bean representing the repository.
            CdiRepositoryBean<?> repositoryBean = createRepositoryBean(repositoryType, qualifiers, beanManager);

            if (LOG.isInfoEnabled()) {
                LOG.info(String.format("Registering bean for %s with qualifiers %s.", repositoryType.getName(), qualifiers));
            }

            // Register the bean to the container.
            registerBean(repositoryBean);
            afterBeanDiscovery.addBean(repositoryBean);
        }
    }

    private <T> CdiRepositoryBean<T> createRepositoryBean(Class<T> repositoryType, Set<Annotation> qualifiers, BeanManager beanManager) {
        // Determine the MongoOperations bean which matches the qualifiers of the repository.
        Bean<DataStoreEntityManager> mongoOperations = this.dataStoreEntityManager.get(qualifiers);

        if (mongoOperations == null) {
            throw new UnsatisfiedResolutionException(String.format("Unable to resolve a bean for '%s' with qualifiers %s.",
                    DataStoreEntityManager.class.getName(), qualifiers));
        }
        // Construct and return the repository bean.
        return new DataStoreRepositoryBean<T>(mongoOperations, qualifiers, repositoryType, beanManager, getCustomImplementationDetector());
    }
}
