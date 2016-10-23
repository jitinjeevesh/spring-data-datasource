package com.virgin.dao.repository.config;

import org.springframework.data.repository.config.RepositoryBeanDefinitionRegistrarSupport;
import org.springframework.data.repository.config.RepositoryConfigurationExtension;

import java.lang.annotation.Annotation;

class DataStoreRepositoriesRegistrar extends RepositoryBeanDefinitionRegistrarSupport {

	@Override
	protected Class<? extends Annotation> getAnnotation() {
		return EnableDataStoreRepositories.class;
	}

	@Override
	protected RepositoryConfigurationExtension getExtension() {
		return new DataStoreRepositoryConfigExtension();
	}
}
