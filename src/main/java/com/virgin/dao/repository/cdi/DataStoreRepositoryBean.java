/*
 * Copyright 2012-2014 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.virgin.dao.repository.cdi;

import com.virgin.dao.DataStoreEntityManager;
import com.virgin.dao.repository.support.DataStoreRepositoryFactory;
import org.springframework.data.repository.cdi.CdiRepositoryBean;
import org.springframework.data.repository.config.CustomRepositoryImplementationDetector;
import org.springframework.util.Assert;

import javax.enterprise.context.spi.CreationalContext;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.BeanManager;
import java.lang.annotation.Annotation;
import java.util.Set;

/**
 * {@link CdiRepositoryBean} to create Datastore  repository instances.
 */
public class DataStoreRepositoryBean<T> extends CdiRepositoryBean<T> {

    private final Bean<DataStoreEntityManager> operations;

    public DataStoreRepositoryBean(Bean<DataStoreEntityManager> dataStoreEntityManagerBean, Set<Annotation> qualifiers, Class<T> repositoryType,
                                   BeanManager beanManager, CustomRepositoryImplementationDetector detector) {

        super(qualifiers, repositoryType, beanManager, detector);

        Assert.notNull(dataStoreEntityManagerBean);
        this.operations = dataStoreEntityManagerBean;
    }

    @Override
    protected T create(CreationalContext<T> creationalContext, Class<T> repositoryType, Object customImplementation) {
        // Get an instance from the associated entity manager bean.
        DataStoreEntityManager dataStoreEntityManager = getDependencyInstance(operations, DataStoreEntityManager.class);

        // Create the Datastore repository instance and return it.
        DataStoreRepositoryFactory factory = new DataStoreRepositoryFactory(dataStoreEntityManager);
        return factory.getRepository(repositoryType, customImplementation);
    }
}
